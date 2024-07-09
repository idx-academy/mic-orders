package com.academy.orders.application.order.usecase;

import com.academy.orders.application.ModelUtils;
import com.academy.orders.domain.order.entity.OrderItem;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CalculateOrderTotalPriceUseCaseTest {
	@InjectMocks
	private CalculateOrderTotalPriceUseCaseImpl calculateOrderTotalPriceUseCase;

	@Test
	void calculateTotalTest() {
		// Given
		OrderItem orderItem = ModelUtils.getOrderItem();

		// When
		BigDecimal actual = calculateOrderTotalPriceUseCase.calculateTotalPrice(List.of(orderItem, orderItem));

		// Then
		assertEquals(orderItem.price().add(orderItem.price()), actual);
	}
}
