package com.academy.orders.application.order.usecase;

import com.academy.orders.domain.account.entity.enumerated.Role;
import com.academy.orders.domain.account.exception.AccountRoleNotFoundException;
import com.academy.orders.domain.account.repository.AccountRepository;
import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders.domain.order.exception.InvalidOrderStatusTransitionException;
import com.academy.orders.domain.order.exception.OrderFinalStateException;
import com.academy.orders.domain.order.exception.OrderNotFoundException;
import com.academy.orders.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static com.academy.orders.application.ModelUtils.getCanceledOrder;
import static com.academy.orders.application.ModelUtils.getOrder;
import static com.academy.orders.application.TestConstants.TEST_MANAGER_MAIL;
import static com.academy.orders.application.TestConstants.TEST_UUID;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateOrderStatusUseCaseTest {
	@InjectMocks
	private UpdateOrderStatusUseCaseImpl updateOrderStatusUseCase;
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private AccountRepository accountRepository;

	@Test
	void updateOrderStatusWithRoleManagerTest() {
		var orderId = TEST_UUID;
		var status = OrderStatus.DELIVERED;
		var role = Role.ROLE_MANAGER;

		when(orderRepository.findById(orderId)).thenReturn(Optional.of(getOrder()));
		when(accountRepository.findRoleByEmail(TEST_MANAGER_MAIL)).thenReturn(Optional.of(role));

		var result = updateOrderStatusUseCase.updateOrderStatus(orderId, status, TEST_MANAGER_MAIL);
		assertThat(result).containsExactlyInAnyOrder("COMPLETED", "CANCELED");

		verify(orderRepository).updateOrderStatus(orderId, status);
		verify(accountRepository).findRoleByEmail(TEST_MANAGER_MAIL);
	}

	@Test
	void updateOrderStatusWithRoleAdminTest() {
		var orderId = TEST_UUID;
		var status = OrderStatus.IN_PROGRESS;
		var role = Role.ROLE_ADMIN;

		when(orderRepository.findById(orderId)).thenReturn(Optional.of(getOrder()));
		when(accountRepository.findRoleByEmail(TEST_MANAGER_MAIL)).thenReturn(Optional.of(role));

		var result = updateOrderStatusUseCase.updateOrderStatus(orderId, status, TEST_MANAGER_MAIL);
		assertThat(result).containsExactlyInAnyOrder("IN_PROGRESS", "SHIPPED", "DELIVERED", "COMPLETED", "CANCELED");

		verify(orderRepository).updateOrderStatus(orderId, status);
		verify(accountRepository).findRoleByEmail(TEST_MANAGER_MAIL);
	}

	@Test
	void updateOrderStatusThrowsOrderNotFoundExceptionTest() {
		var orderId = TEST_UUID;
		var status = OrderStatus.COMPLETED;
		when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

		assertThrows(OrderNotFoundException.class,
				() -> updateOrderStatusUseCase.updateOrderStatus(orderId, status, TEST_MANAGER_MAIL));
		verify(orderRepository).findById(orderId);
	}

	@Test
	void updateOrderStatusThrowsAccountNotFoundExceptionTest() {
		var orderId = TEST_UUID;
		var status = OrderStatus.COMPLETED;

		when(orderRepository.findById(orderId)).thenReturn(Optional.of(getOrder()));
		when(accountRepository.findRoleByEmail(TEST_MANAGER_MAIL)).thenReturn(Optional.empty());

		assertThrows(AccountRoleNotFoundException.class,
				() -> updateOrderStatusUseCase.updateOrderStatus(orderId, status, TEST_MANAGER_MAIL));

		verify(orderRepository).findById(orderId);
		verify(accountRepository).findRoleByEmail(TEST_MANAGER_MAIL);
	}

	@Test
	void updateOrderStatusThrowsInvalidOrderStatusTransitionExceptionTest() {
		var orderId = TEST_UUID;
		var status = OrderStatus.IN_PROGRESS;
		var role = Role.ROLE_MANAGER;

		when(orderRepository.findById(orderId)).thenReturn(Optional.of(getOrder()));
		when(accountRepository.findRoleByEmail(TEST_MANAGER_MAIL)).thenReturn(Optional.of(role));

		assertThrows(InvalidOrderStatusTransitionException.class,
				() -> updateOrderStatusUseCase.updateOrderStatus(orderId, status, TEST_MANAGER_MAIL));

		verify(orderRepository).findById(orderId);
		verify(accountRepository).findRoleByEmail(TEST_MANAGER_MAIL);
	}

	@Test
	void updateOrderStatusThrowsOrderFinalStateExceptionTest() {
		var orderId = TEST_UUID;
		var status = OrderStatus.IN_PROGRESS;
		var role = Role.ROLE_MANAGER;

		when(orderRepository.findById(orderId)).thenReturn(Optional.of(getCanceledOrder()));
		when(accountRepository.findRoleByEmail(TEST_MANAGER_MAIL)).thenReturn(Optional.of(role));

		assertThrows(OrderFinalStateException.class,
				() -> updateOrderStatusUseCase.updateOrderStatus(orderId, status, TEST_MANAGER_MAIL));

		verify(orderRepository).findById(orderId);
		verify(accountRepository).findRoleByEmail(TEST_MANAGER_MAIL);
	}
}
