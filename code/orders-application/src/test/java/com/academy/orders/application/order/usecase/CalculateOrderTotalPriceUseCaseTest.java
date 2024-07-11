package com.academy.orders.application.order.usecase;

import com.academy.orders.application.ModelUtils;
import com.academy.orders.domain.order.entity.Order;
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
	void calculateTotalPriceTest() {
		// Given
		OrderItem orderItem = ModelUtils.getOrderItem();

		// When
		BigDecimal actual = calculateOrderTotalPriceUseCase.calculateTotalPrice(List.of(orderItem, orderItem));

		// Then
		assertEquals(orderItem.price().add(orderItem.price()), actual);
	}

	@Test
	void calculateTotalPriceForOrderTest() {
		// Given
		Order orderWithoutTotal = ModelUtils.getOrderWithoutTotal();
		Order orderWithTotal = ModelUtils.getOrder();

		// When
		Order actual = calculateOrderTotalPriceUseCase.calculateTotalPriceFor(orderWithoutTotal);

		// Then
		assertEquals(orderWithTotal, actual);
	}

	@Test
	void calculateTotalPriceForOrderListTest() {
		// Given
		Order orderWithoutTotal = ModelUtils.getOrderWithoutTotal();
		Order orderWithTotal = ModelUtils.getOrder();
		List<Order> ordersWithoutTotal = List.of(orderWithoutTotal, orderWithoutTotal);
		List<Order> ordersWithTotal = List.of(orderWithTotal, orderWithTotal);

		// When
		List<Order> actual = calculateOrderTotalPriceUseCase.calculateTotalPriceFor(ordersWithoutTotal);

		// Then
		assertEquals(ordersWithTotal, actual);
	}
}
