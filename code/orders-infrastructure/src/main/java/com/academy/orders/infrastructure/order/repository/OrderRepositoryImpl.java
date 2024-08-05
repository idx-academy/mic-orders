package com.academy.orders.infrastructure.order.repository;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.order.dto.OrdersFilterParametersDto;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders.domain.order.repository.OrderRepository;
import com.academy.orders.infrastructure.account.repository.AccountJpaAdapter;
import com.academy.orders.infrastructure.common.PageableMapper;
import com.academy.orders.infrastructure.order.OrderMapper;
import com.academy.orders.infrastructure.order.OrderPageMapper;
import com.academy.orders.infrastructure.order.entity.OrderEntity;
import com.academy.orders.infrastructure.product.repository.ProductJpaAdapter;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderRepositoryImpl implements OrderRepository {
	private final OrderJpaAdapter jpaAdapter;
	private final CustomOrderRepository customOrderRepository;
	private final ProductJpaAdapter productJpaAdapter;
	private final AccountJpaAdapter accountJpaAdapter;
	private final OrderMapper mapper;
	private final PageableMapper pageableMapper;
	private final OrderPageMapper pageMapper;

	@Override
	public Optional<Order> findById(UUID id) {
		return jpaAdapter.findById(id).map(mapper::fromEntity);
	}

	@Override
	public Optional<Order> findById(UUID id, String language) {
		Optional<OrderEntity> order = jpaAdapter.findByIdFetchOrderItemsData(id);
		order.ifPresent(orderEntity -> loadProducts(language, orderEntity));
		return order.map(mapper::fromEntity);
	}

	@Override
	public Page<Order> findAllByUserId(Long userId, String language, Pageable pageable) {
		var springPageable = pageableMapper.fromDomain(pageable);
		var orderEntityPage = jpaAdapter.findAllByAccountId(userId, springPageable);
		loadProducts(language, orderEntityPage.getContent().toArray(new OrderEntity[0]));
		return pageMapper.toDomain(orderEntityPage);
	}

	@Override
	@Transactional
	public UUID save(Order order, Long accountId) {
		var orderEntity = getOrderEntityWithPostAddress(order);
		addAccountToOrder(orderEntity, accountId);
		mapOrderItemsWithProductsAndOrder(orderEntity);

		return jpaAdapter.save(orderEntity).getId();
	}

	@Override
	public Page<Order> findAll(OrdersFilterParametersDto filterParametersDto, Pageable pageable) {
		var springPageable = pageableMapper.fromDomain(pageable);
		var orderEntityPage = customOrderRepository.findAllByFilterParameters(filterParametersDto, springPageable);
		return pageMapper.toDomain(orderEntityPage);
	}

	@Override
	public Optional<Order> findByIdFetchData(UUID orderId) {
		return jpaAdapter.findByIdFetchData(orderId).map(mapper::fromEntity);
	}

	@Override
	@Transactional
	public void updateOrderStatus(UUID orderId, OrderStatus orderStatus) {
		jpaAdapter.updateOrderStatus(orderId, orderStatus);
	}

	@Override
	@Transactional
	public void updateIsPaidStatus(UUID orderId, Boolean isPaid) {
		jpaAdapter.updateIsPaidStatus(orderId, isPaid);
	}

	private OrderEntity getOrderEntityWithPostAddress(Order order) {
		var orderEntity = mapper.toEntity(order);
		orderEntity.getPostAddress().setOrder(orderEntity);
		return orderEntity;
	}

	private void addAccountToOrder(OrderEntity orderEntity, Long accountId) {
		orderEntity.setAccount(accountJpaAdapter.getReferenceById(accountId));
	}

	private void mapOrderItemsWithProductsAndOrder(OrderEntity orderEntity) {
		orderEntity.getOrderItems().forEach(item -> {
			item.setProduct(productJpaAdapter.getReferenceById(item.getProduct().getId()));
			item.setOrder(orderEntity);
		});
	}

	private void loadProducts(String language, OrderEntity... orderEntities) {
		var productIds = Arrays.stream(orderEntities).flatMap(orderEntity -> orderEntity.getOrderItems().stream())
				.map(orderItemEntity -> orderItemEntity.getProduct().getId()).toList();
		productJpaAdapter.findAllByIdAndLanguageCode(productIds, language);
	}
}