package com.academy.orders.application.order.usecase;

import com.academy.orders.application.ModelUtils;
import com.academy.orders.domain.account.entity.enumerated.Role;
import com.academy.orders.domain.account.repository.AccountRepository;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.OrderItem;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.academy.orders.application.ModelUtils.getOrderManagementForAdmin;
import static com.academy.orders.application.ModelUtils.getOrderManagementForManager;
import static com.academy.orders.application.ModelUtils.getOrderWithoutTotal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CalculateOrderTotalPriceUseCaseTest {
	@InjectMocks
	private CalculateOrderTotalPriceUseCaseImpl calculateOrderTotalPriceUseCase;

	@Mock
	private AccountRepository accountRepository;

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
		Order orderWithoutTotal = getOrderWithoutTotal();
		Order orderWithTotal = ModelUtils.getOrder();

		// When
		Order actual = calculateOrderTotalPriceUseCase.calculateTotalPriceFor(orderWithoutTotal);

		// Then
		assertEquals(orderWithTotal, actual);
	}

	@Test
	void calculateTotalPriceForOrderListTest() {
		// Given
		Order orderWithoutTotal = getOrderWithoutTotal();
		Order orderWithTotal = ModelUtils.getOrder();
		List<Order> ordersWithoutTotal = List.of(orderWithoutTotal, orderWithoutTotal);
		List<Order> ordersWithTotal = List.of(orderWithTotal, orderWithTotal);

		// When
		List<Order> actual = calculateOrderTotalPriceUseCase.calculateTotalPriceFor(ordersWithoutTotal);

		// Then
		assertEquals(ordersWithTotal, actual);
	}

	@Test
	void calculateTotalPriceForOrderListIfNullTest() {
		// When
		List<Order> actual = calculateOrderTotalPriceUseCase.calculateTotalPriceFor((List<Order>) null);

		// Then
		assertTrue(actual.isEmpty());
	}

	@Test
	void calculateTotalPriceAndAvailableStatusesForManagerTest() {
		var order = getOrderWithoutTotal();
		var orderManagement = getOrderManagementForManager();
		var orders = List.of(order);
		var actual = calculateOrderTotalPriceUseCase.calculateTotalPriceAndAvailableStatuses(orders,
				String.valueOf(Role.ROLE_MANAGER));
		assertEquals(List.of(orderManagement), actual);
	}

	@Test
	void calculateTotalPriceAndAvailableStatusesForAdminTest() {
		var order = getOrderWithoutTotal();
		var orderManagement = getOrderManagementForAdmin();
		var orders = List.of(order);
		var actual = calculateOrderTotalPriceUseCase.calculateTotalPriceAndAvailableStatuses(orders,
				String.valueOf(Role.ROLE_ADMIN));
		assertEquals(List.of(orderManagement), actual);
	}

	@Test
	void calculateTotalPriceAndAvailableStatusesIfNullTest() {
		var actual = calculateOrderTotalPriceUseCase.calculateTotalPriceAndAvailableStatuses(null,
				String.valueOf(Role.ROLE_MANAGER));
		assertTrue(actual.isEmpty());
	}
}
