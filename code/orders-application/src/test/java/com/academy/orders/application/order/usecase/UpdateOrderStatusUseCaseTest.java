package com.academy.orders.application.order.usecase;

import com.academy.orders.domain.exception.NotFoundException;
import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.UUID;
import static com.academy.orders.application.ModelUtils.getOrder;
import static com.academy.orders.application.TestConstants.TEST_UUID;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateOrderStatusUseCaseTest {
	@InjectMocks
	private UpdateOrderStatusUseCaseImpl updateOrderStatusUseCase;
	@Mock
	private OrderRepository orderRepository;

	@Test
	void updateOrderStatusTest() {
		UUID orderId = TEST_UUID;
		OrderStatus status = OrderStatus.COMPLETED;
		when(orderRepository.findById(orderId)).thenReturn(Optional.of(getOrder()));

		updateOrderStatusUseCase.updateOrderStatus(orderId, status);
		verify(orderRepository).updateOrderStatus(orderId, status);
	}

	@Test
	void updateOrderStatusThrowsNotFoundExceptionTest() {
		UUID orderId = TEST_UUID;
		OrderStatus status = OrderStatus.COMPLETED;
		when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> updateOrderStatusUseCase.updateOrderStatus(orderId, status));
		verify(orderRepository).findById(orderId);
	}
}
