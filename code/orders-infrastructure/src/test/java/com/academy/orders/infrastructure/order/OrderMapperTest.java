package com.academy.orders.infrastructure.order;

import com.academy.orders.infrastructure.ModelUtils;
import com.academy.orders.infrastructure.product.entity.ProductEntity;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class OrderMapperTest {
	@Spy
	private OrderMapper orderMapper;

	@Test
	void isNotLazyLoadedReturnsTrueTest() {
		ProductEntity productEntity = ModelUtils.getProductEntity();
		try (MockedStatic<Hibernate> hibernateMockedStatic = mockStatic(Hibernate.class)) {
			hibernateMockedStatic.when(() -> Hibernate.isInitialized(productEntity)).thenReturn(true);
			assertTrue(orderMapper.isNotLazyLoaded(productEntity));
		}
	}

	@Test
	void isNotLazyLoadedReturnsFalseTest() {
		ProductEntity productEntity = ModelUtils.getProductEntity();
		try (MockedStatic<Hibernate> hibernateMockedStatic = mockStatic(Hibernate.class)) {
			hibernateMockedStatic.when(() -> Hibernate.isInitialized(productEntity)).thenReturn(false);
			assertFalse(orderMapper.isNotLazyLoaded(productEntity));
		}
	}
}
