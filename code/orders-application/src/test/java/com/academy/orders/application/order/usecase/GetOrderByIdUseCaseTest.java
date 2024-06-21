package com.academy.orders.application.order.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import java.util.Optional;
import java.util.UUID;

import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.repository.OrderRepository;
import com.academy.orders.domain.order.exception.OrderNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GetOrderByIdUseCaseTest {

	@Mock
	private OrderRepository orderRepository;

	@InjectMocks
	private GetOrderByIdUseCaseImpl getOrderByIdUseCase;

	private UUID orderId;
	private Order order;

	@BeforeEach
	public void setUp() {
		orderId = UUID.randomUUID();
		order = Order.builder().id(orderId).build();
	}

	@Test
	void testGetOrderById_OrderExists() {
		when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

		Order result = getOrderByIdUseCase.getOrderById(orderId);

		assertNotNull(result);
		assertEquals(order, result);
		verify(orderRepository, times(1)).findById(orderId);
	}

	@Test
	void testGetOrderById_OrderNotFound() {
		when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

		var exception = assertThrows(OrderNotFoundException.class, () -> getOrderByIdUseCase.getOrderById(orderId));

		assertEquals(orderId, exception.getOrderId());
		verify(orderRepository, times(1)).findById(orderId);
	}
}
