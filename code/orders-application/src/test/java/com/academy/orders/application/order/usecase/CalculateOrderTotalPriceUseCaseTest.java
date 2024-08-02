package com.academy.orders.application.order.usecase;

import com.academy.orders.application.ModelUtils;
import com.academy.orders.domain.account.entity.enumerated.Role;
import com.academy.orders.domain.account.exception.AccountRoleNotFoundException;
import com.academy.orders.domain.account.repository.AccountRepository;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.OrderItem;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.academy.orders.application.ModelUtils.getOrderManagementForAdmin;
import static com.academy.orders.application.ModelUtils.getOrderManagementForManager;
import static com.academy.orders.application.ModelUtils.getOrderWithoutTotal;
import static com.academy.orders.application.TestConstants.TEST_ADMIN_MAIL;
import static com.academy.orders.application.TestConstants.TEST_MANAGER_MAIL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
		when(accountRepository.findRoleByEmail(TEST_MANAGER_MAIL)).thenReturn(Optional.of(Role.ROLE_MANAGER));
		var actual = calculateOrderTotalPriceUseCase.calculateTotalPriceAndAvailableStatuses(orders, TEST_MANAGER_MAIL);
		assertEquals(List.of(orderManagement), actual);
		verify(accountRepository).findRoleByEmail(TEST_MANAGER_MAIL);
	}

	@Test
	void calculateTotalPriceAndAvailableStatusesForAdminTest() {
		var order = getOrderWithoutTotal();
		var orderManagement = getOrderManagementForAdmin();
		var orders = List.of(order);
		when(accountRepository.findRoleByEmail(TEST_ADMIN_MAIL)).thenReturn(Optional.of(Role.ROLE_ADMIN));
		var actual = calculateOrderTotalPriceUseCase.calculateTotalPriceAndAvailableStatuses(orders, TEST_ADMIN_MAIL);
		assertEquals(List.of(orderManagement), actual);
		verify(accountRepository).findRoleByEmail(TEST_ADMIN_MAIL);
	}

	@Test
	void calculateTotalPriceAndAvailableStatusesIfNullTest() {
		var actual = calculateOrderTotalPriceUseCase.calculateTotalPriceAndAvailableStatuses(null, TEST_MANAGER_MAIL);
		assertTrue(actual.isEmpty());
	}

	@Test
	void calculateTotalPriceAndAvailableStatusesThrowAccountNotFoundExceptionTest() {
		var order = getOrderWithoutTotal();
		var orders = List.of(order);
		when(accountRepository.findRoleByEmail(TEST_ADMIN_MAIL)).thenReturn(Optional.empty());
		assertThrows(AccountRoleNotFoundException.class,
				() -> calculateOrderTotalPriceUseCase.calculateTotalPriceAndAvailableStatuses(orders, TEST_ADMIN_MAIL));
		verify(accountRepository).findRoleByEmail(TEST_ADMIN_MAIL);
	}
}
