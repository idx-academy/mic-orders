package com.academy.orders.application.order.usecase;

import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UpdateOrderStatusUseCaseTest {
	@InjectMocks
	private UpdateOrderStatusUseCaseImpl updateOrderStatusUseCase;
	@Mock
	private OrderRepository orderRepository;

	@Test
	void testUpdateOrderStatus() {
		UUID orderId = UUID.randomUUID();
		OrderStatus status = OrderStatus.COMPLETED;
		updateOrderStatusUseCase.updateOrderStatus(orderId, status);
		verify(orderRepository).updateOrderStatus(orderId, status);
	}
}
