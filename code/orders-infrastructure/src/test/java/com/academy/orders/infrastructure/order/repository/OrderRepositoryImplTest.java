package com.academy.orders.infrastructure.order.repository;

import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.infrastructure.account.entity.AccountEntity;
import com.academy.orders.infrastructure.account.repository.AccountJpaAdapter;
import com.academy.orders.infrastructure.order.OrderMapper;
import com.academy.orders.infrastructure.order.entity.OrderEntity;
import com.academy.orders.infrastructure.product.entity.ProductEntity;
import com.academy.orders.infrastructure.product.repository.ProductJpaAdapter;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.academy.orders.infrastructure.ModelUtils.getAccountEntity;
import static com.academy.orders.infrastructure.ModelUtils.getOrder;
import static com.academy.orders.infrastructure.ModelUtils.getOrderEntity;
import static com.academy.orders.infrastructure.ModelUtils.getOrderItemEntity;
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

		setUpMocksForSaveMethod(orderEntity, accountEntity, productEntity);

		var orderId = orderRepository.save(getOrder(), 1L);

		assertEquals(orderEntity.getId(), orderId);
		assertEquals(postAddressEntity.getOrder(), orderEntity);
		assertEquals(orderEntity.getAccount(), accountEntity);
		assertEquals(orderItemEntity.getOrder(), orderEntity);

		verifyMocksOfSaveMethod();
	}

	private void setUpMocksForSaveMethod(OrderEntity orderEntity, AccountEntity accountEntity,
			ProductEntity productEntity) {
		when(mapper.toEntity(any(Order.class))).thenReturn(orderEntity);
		when(accountJpaAdapter.getReferenceById(anyLong())).thenReturn(accountEntity);
		when(productJpaAdapter.getReferenceById(any(UUID.class))).thenReturn(productEntity);
		when(jpaAdapter.save(any())).thenReturn(orderEntity);
	}

	private void verifyMocksOfSaveMethod() {
		verify(mapper).toEntity(any(Order.class));
		verify(accountJpaAdapter).getReferenceById(anyLong());
		verify(productJpaAdapter).getReferenceById(any(UUID.class));
		verify(jpaAdapter).save(any());
	}
}
