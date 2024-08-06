package com.academy.orders.application.order.usecase;

import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders.domain.order.exception.OrderFinalStateException;
import com.academy.orders.domain.order.exception.OrderNotFoundException;
import com.academy.orders.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.UUID;

import static com.academy.orders.application.ModelUtils.getCanceledOrder;
import static com.academy.orders.application.ModelUtils.getCompletedOrder;
import static com.academy.orders.application.ModelUtils.getOrder;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CancelOrderUseCaseTest {
	@Mock
	private OrderRepository orderRepository;
	@InjectMocks
	private CancelOrderUseCaseImpl cancelOrderUseCase;

	private final Long userId = 1L;
	private final Order order = getOrder();

	@Test
	void cancelOrderTest() {
		// Given
		UUID orderId = order.id();
		when(orderRepository.findByIdFetchData(orderId)).thenReturn(Optional.of(order));
		doNothing().when(orderRepository).updateOrderStatus(orderId, OrderStatus.CANCELED);

		// When
		cancelOrderUseCase.cancelOrder(userId, orderId);

		// Then
		verify(orderRepository).findByIdFetchData(orderId);
		verify(orderRepository).updateOrderStatus(orderId, OrderStatus.CANCELED);
	}

	@Test
	void cancelOrderThrowsOrderNotFoundExceptionWhenOrderNotFoundTest() {
		// Given
		UUID orderId = order.id();
		when(orderRepository.findByIdFetchData(orderId)).thenReturn(Optional.empty());

		// Then
		assertThrows(OrderNotFoundException.class, () -> cancelOrderUseCase.cancelOrder(userId, orderId));
		verify(orderRepository).findByIdFetchData(orderId);
	}

	@Test
	void cancelOrderThrowsOrderFinalStateExceptionWhenOrderCanceledTest() {
		Order canceledOrder = getCanceledOrder();
		// Given
		UUID orderId = canceledOrder.id();
		when(orderRepository.findByIdFetchData(orderId)).thenReturn(Optional.of(canceledOrder));

		// Then
		assertThrows(OrderFinalStateException.class, () -> cancelOrderUseCase.cancelOrder(userId, orderId));
		verify(orderRepository).findByIdFetchData(orderId);
	}

	@Test
	void cancelOrderThrowsOrderFinalStateExceptionWhenOrderCompletedTest() {
		Order completedOrder = getCompletedOrder();
		// Given
		UUID orderId = completedOrder.id();
		when(orderRepository.findByIdFetchData(orderId)).thenReturn(Optional.of(completedOrder));

		// Then
		assertThrows(OrderFinalStateException.class, () -> cancelOrderUseCase.cancelOrder(userId, orderId));
		verify(orderRepository).findByIdFetchData(orderId);
	}
}
