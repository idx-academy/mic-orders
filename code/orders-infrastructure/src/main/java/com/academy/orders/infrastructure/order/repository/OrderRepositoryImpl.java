package com.academy.orders.infrastructure.order.repository;

import com.academy.colors_api.generated.api.ColorsApi;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.repository.OrderRepository;
import com.academy.orders.infrastructure.account.repository.AccountJpaAdapter;
import com.academy.orders.infrastructure.order.OrderMapper;
import com.academy.orders.infrastructure.order.entity.OrderEntity;
import com.academy.orders.infrastructure.product.repository.ProductJpaAdapter;
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
	private final ProductJpaAdapter productJpaAdapter;
	private final AccountJpaAdapter accountJpaAdapter;

	private final OrderMapper mapper;

	// TODO remove, added for example. It should be created separate repo
	// ColorsRepository
	private final ColorsApi colorsApi;

	@Override
	public Optional<Order> findById(UUID id) {
		try {
			// TODO delete me
			final var colors = colorsApi.getColors();
			log.info("Retrieved colors {}", colors);
		} catch (Exception e) {
			log.error("Ops", e);
		}

		return jpaAdapter.findById(id).map(mapper::fromEntity);
	}

	@Override
	@Transactional
	public UUID save(Order order, Long accountId) {
		var orderEntity = getOrderEntityWithPostAddress(order);
		addAccountToOrder(orderEntity, accountId);
		mapOrderItemsWithProductsAndOrder(orderEntity);

		return jpaAdapter.save(orderEntity).getId();
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
}