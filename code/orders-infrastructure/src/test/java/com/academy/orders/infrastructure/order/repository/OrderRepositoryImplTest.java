package com.academy.orders.infrastructure.order.repository;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.infrastructure.account.repository.AccountJpaAdapter;
import com.academy.orders.infrastructure.common.PageableMapper;
import com.academy.orders.infrastructure.order.OrderMapper;
import com.academy.orders.infrastructure.order.OrderPageMapper;
import com.academy.orders.infrastructure.product.repository.ProductJpaAdapter;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static com.academy.orders.infrastructure.ModelUtils.getAccountEntity;
import static com.academy.orders.infrastructure.ModelUtils.getOrder;
import static com.academy.orders.infrastructure.ModelUtils.getOrderEntity;
import static com.academy.orders.infrastructure.ModelUtils.getOrderItemEntity;
import static com.academy.orders.infrastructure.ModelUtils.getPageImplOf;
import static com.academy.orders.infrastructure.ModelUtils.getPageOf;
import static com.academy.orders.infrastructure.ModelUtils.getPageable;
import static com.academy.orders.infrastructure.ModelUtils.getPostAddressEntity;
import static com.academy.orders.infrastructure.ModelUtils.getProductEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderRepositoryImplTest {
	@InjectMocks
	private OrderRepositoryImpl orderRepository;
	@Mock
	private OrderJpaAdapter jpaAdapter;
	@Mock
	private ProductJpaAdapter productJpaAdapter;
	@Mock
	private AccountJpaAdapter accountJpaAdapter;
	@Mock
	private OrderMapper mapper;
	@Mock
	private PageableMapper pageableMapper;
	@Mock
	private OrderPageMapper pageMapper;

	@Test
	void testSave() {
		var orderEntity = getOrderEntity();
		var productEntity = getProductEntity();
		var orderItemEntity = getOrderItemEntity();
		orderItemEntity.setProduct(productEntity);
		orderEntity.addOrderItems(Collections.singletonList(orderItemEntity));

		var postAddressEntity = getPostAddressEntity();
		orderEntity.setPostAddress(postAddressEntity);
		var accountEntity = getAccountEntity();

		when(mapper.toEntity(any(Order.class))).thenReturn(orderEntity);
		when(accountJpaAdapter.getReferenceById(anyLong())).thenReturn(accountEntity);
		when(productJpaAdapter.getReferenceById(any(UUID.class))).thenReturn(productEntity);
		when(jpaAdapter.save(any())).thenReturn(orderEntity);

		var orderId = orderRepository.save(getOrder(), 1L);

		assertEquals(orderEntity.getId(), orderId);
		assertEquals(postAddressEntity.getOrder(), orderEntity);
		assertEquals(orderEntity.getAccount(), accountEntity);
		assertEquals(orderItemEntity.getOrder(), orderEntity);

		verify(mapper).toEntity(any(Order.class));
		verify(accountJpaAdapter).getReferenceById(anyLong());
		verify(productJpaAdapter).getReferenceById(any(UUID.class));
		verify(jpaAdapter).save(any());
	}

	@Test
	void findAllByUserIdTest() {
		// Given
		Long userId = 1L;
		String language = "ua";
		Pageable pageable = getPageable();
		var orderDomainPage = getPageOf(getOrder());
		var springPageable = PageRequest.of(pageable.page(), pageable.size());
		var orderEntityPage = getPageImplOf(getOrderEntity());
		var productIds = orderEntityPage.getContent().stream()
				.flatMap(orderEntity -> orderEntity.getOrderItems().stream())
				.map(orderItemEntity -> orderItemEntity.getProduct().getId()).toList();
		var products = List.of(getProductEntity());

		when(pageableMapper.fromDomain(pageable)).thenReturn(springPageable);
		when(jpaAdapter.findAllByAccount_Id(userId, springPageable)).thenReturn(orderEntityPage);
		when(productJpaAdapter.findAllByIdAndLanguageCode(productIds, language)).thenReturn(products);
		when(pageMapper.toDomain(orderEntityPage)).thenReturn(orderDomainPage);

		// When
		Page<Order> actual = orderRepository.findAllByUserId(userId, language, pageable);

		// Then
		assertEquals(orderDomainPage, actual);
		verify(pageableMapper).fromDomain(pageable);
		verify(jpaAdapter).findAllByAccount_Id(userId, springPageable);
		verify(productJpaAdapter).findAllByIdAndLanguageCode(productIds, language);
		verify(pageMapper).toDomain(orderEntityPage);
	}
}
