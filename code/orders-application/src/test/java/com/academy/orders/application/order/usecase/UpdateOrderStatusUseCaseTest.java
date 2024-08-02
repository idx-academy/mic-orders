package com.academy.orders.application.order.usecase;

import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders.domain.order.exception.InvalidOrderStatusTransitionException;
import com.academy.orders.domain.order.exception.OrderAlreadyPaidException;
import com.academy.orders.domain.order.exception.OrderFinalStateException;
import com.academy.orders.domain.order.exception.OrderNotFoundException;
import com.academy.orders.domain.order.exception.OrderUnpaidException;
import com.academy.orders.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static com.academy.orders.application.ModelUtils.getCanceledOrder;
import static com.academy.orders.application.ModelUtils.getDeliveredOrder;
import static com.academy.orders.application.ModelUtils.getOrder;
import static com.academy.orders.application.ModelUtils.getOrderStatusInfo;
import static com.academy.orders.application.ModelUtils.getPaidOrder;
import static com.academy.orders.application.ModelUtils.getUpdateOrderStatusDto;
import static com.academy.orders.application.ModelUtils.getUpdateOrderStatusDtoWithCompletedStatus;
import static com.academy.orders.application.ModelUtils.getUpdateOrderStatusDtoWithNullIsPaid;
import static com.academy.orders.application.ModelUtils.getUpdateOrderStatusDtoWithNullIsPaidAndStatusCompleted;
import static com.academy.orders.application.TestConstants.ROLE_ADMIN;
import static com.academy.orders.application.TestConstants.ROLE_MANAGER;
import static com.academy.orders.application.TestConstants.TEST_UUID;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateOrderStatusUseCaseTest {
	@InjectMocks
	private UpdateOrderStatusUseCaseImpl updateOrderStatusUseCase;
	@Mock
	private OrderRepository orderRepository;

	@Test
	void updateOrderStatusWithRoleManagerTest() {
		var orderId = TEST_UUID;
		var status = OrderStatus.IN_PROGRESS;

		var updateOrderStatusDto = getUpdateOrderStatusDto();
		var orderStatusInfo = getOrderStatusInfo();

		when(orderRepository.findById(orderId)).thenReturn(Optional.of(getOrder()));
		doNothing().when(orderRepository).updateOrderStatus(orderId, status);
		doNothing().when(orderRepository).updateIsPaidStatus(orderId, false);

		var result = updateOrderStatusUseCase.updateOrderStatus(orderId, updateOrderStatusDto, ROLE_MANAGER);

		assertEquals(orderStatusInfo.isPaid(), result.isPaid());
		assertThat(result.availableStatuses()).containsExactlyInAnyOrder(OrderStatus.SHIPPED, OrderStatus.DELIVERED,
				OrderStatus.COMPLETED, OrderStatus.CANCELED);

		verify(orderRepository).updateOrderStatus(orderId, status);
		verify(orderRepository).updateOrderStatus(orderId, status);
		verify(orderRepository).updateIsPaidStatus(orderId, false);
	}

	@Test
	void updateOrderStatusWithRoleAdminTest() {
		var orderId = TEST_UUID;
		var status = OrderStatus.IN_PROGRESS;

		var updateOrderStatusDto = getUpdateOrderStatusDto();
		var orderStatusInfo = getOrderStatusInfo();

		when(orderRepository.findById(orderId)).thenReturn(Optional.of(getCanceledOrder()));
		doNothing().when(orderRepository).updateOrderStatus(orderId, status);
		doNothing().when(orderRepository).updateIsPaidStatus(orderId, false);

		var result = updateOrderStatusUseCase.updateOrderStatus(orderId, updateOrderStatusDto, ROLE_ADMIN);

		assertEquals(orderStatusInfo.isPaid(), result.isPaid());
		assertThat(result.availableStatuses()).containsExactlyInAnyOrder(OrderStatus.IN_PROGRESS, OrderStatus.SHIPPED,
				OrderStatus.DELIVERED, OrderStatus.COMPLETED, OrderStatus.CANCELED);

		verify(orderRepository).updateOrderStatus(orderId, status);
		verify(orderRepository).updateOrderStatus(orderId, status);
		verify(orderRepository).updateIsPaidStatus(orderId, false);
	}

	@Test
	void updateOrderStatusThrowsOrderNotFoundExceptionTest() {
		var orderId = TEST_UUID;
		var updateOrderStatusDto = getUpdateOrderStatusDto();

		when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

		assertThrows(OrderNotFoundException.class,
				() -> updateOrderStatusUseCase.updateOrderStatus(orderId, updateOrderStatusDto, ROLE_MANAGER));
		verify(orderRepository).findById(orderId);
	}

	@Test
	void updateOrderStatusThrowsInvalidOrderStatusTransitionExceptionTest() {
		var orderId = TEST_UUID;
		var updateOrderStatusDto = getUpdateOrderStatusDto();

		when(orderRepository.findById(orderId)).thenReturn(Optional.of(getDeliveredOrder()));

		assertThrows(InvalidOrderStatusTransitionException.class,
				() -> updateOrderStatusUseCase.updateOrderStatus(orderId, updateOrderStatusDto, ROLE_MANAGER));

		verify(orderRepository).findById(orderId);
	}

	@Test
	void updateOrderStatusThrowsOrderFinalStateExceptionTest() {
		var orderId = TEST_UUID;
		var updateOrderStatusDto = getUpdateOrderStatusDto();

		when(orderRepository.findById(orderId)).thenReturn(Optional.of(getCanceledOrder()));

		assertThrows(OrderFinalStateException.class,
				() -> updateOrderStatusUseCase.updateOrderStatus(orderId, updateOrderStatusDto, ROLE_MANAGER));

		verify(orderRepository).findById(orderId);
	}

	@Test
	void updateOrderStatusThrowsOrderAlreadyPaidExceptionTest() {
		var orderId = TEST_UUID;
		var updateOrderStatusDto = getUpdateOrderStatusDto();

		when(orderRepository.findById(orderId)).thenReturn(Optional.of(getPaidOrder()));

		assertThrows(OrderAlreadyPaidException.class,
				() -> updateOrderStatusUseCase.updateOrderStatus(orderId, updateOrderStatusDto, ROLE_MANAGER));

		verify(orderRepository).findById(orderId);
	}

	@Test
	void updateOrderStatusThrowsOrderUnpaidExceptionTest() {
		var orderId = TEST_UUID;
		var updateOrderStatusDto = getUpdateOrderStatusDtoWithCompletedStatus();

		when(orderRepository.findById(orderId)).thenReturn(Optional.of(getOrder()));

		assertThrows(OrderUnpaidException.class,
				() -> updateOrderStatusUseCase.updateOrderStatus(orderId, updateOrderStatusDto, ROLE_MANAGER));

		verify(orderRepository).findById(orderId);
	}

	@Test
	void updateOrderStatusWithNullIsPaidStatusTest() {
		var orderId = TEST_UUID;
		var status = OrderStatus.IN_PROGRESS;

		var updateOrderStatusDto = getUpdateOrderStatusDtoWithNullIsPaid();
		var orderStatusInfo = getOrderStatusInfo();

		when(orderRepository.findById(orderId)).thenReturn(Optional.of(getOrder()));
		doNothing().when(orderRepository).updateOrderStatus(orderId, status);

		var result = updateOrderStatusUseCase.updateOrderStatus(orderId, updateOrderStatusDto, ROLE_MANAGER);

		assertEquals(orderStatusInfo.isPaid(), result.isPaid());
		assertThat(result.availableStatuses()).containsExactlyInAnyOrder(OrderStatus.SHIPPED, OrderStatus.DELIVERED,
				OrderStatus.COMPLETED, OrderStatus.CANCELED);

		verify(orderRepository).updateOrderStatus(orderId, status);
		verify(orderRepository).updateOrderStatus(orderId, status);
	}

	@Test
	void updateOrderStatusWithoutPaidStatusToCompletedTest() {
		var orderId = TEST_UUID;
		var status = OrderStatus.COMPLETED;

		var updateOrderStatusDto = getUpdateOrderStatusDtoWithNullIsPaidAndStatusCompleted();

		when(orderRepository.findById(orderId)).thenReturn(Optional.of(getPaidOrder()));
		doNothing().when(orderRepository).updateOrderStatus(orderId, status);

		updateOrderStatusUseCase.updateOrderStatus(orderId, updateOrderStatusDto, ROLE_MANAGER);

		verify(orderRepository).updateOrderStatus(orderId, status);
		verify(orderRepository).updateOrderStatus(orderId, status);
	}
}
