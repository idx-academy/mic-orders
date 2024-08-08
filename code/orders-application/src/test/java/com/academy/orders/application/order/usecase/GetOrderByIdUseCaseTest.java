package com.academy.orders.application.order.usecase;

import com.academy.orders.application.ModelUtils;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.exception.OrderNotFoundException;
import com.academy.orders.domain.order.repository.OrderImageRepository;
import com.academy.orders.domain.order.repository.OrderRepository;
import com.academy.orders.domain.order.usecase.CalculateOrderTotalPriceUseCase;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class GetOrderByIdUseCaseTest {
	@Mock
	private CalculateOrderTotalPriceUseCase calculateOrderTotalPriceUseCase;
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private OrderImageRepository orderImageRepository;
	@InjectMocks
	private GetOrderByIdUseCaseImpl getOrderByIdUseCase;

	private UUID orderId;
	private Order order;
	private Order orderWithoutTotal;
	private String language;

	@BeforeEach
	public void setUp() {
		orderId = UUID.randomUUID();
		orderWithoutTotal = ModelUtils.getOrderWithoutTotal();
		order = ModelUtils.getOrder();
		language = "uk";
	}

	@Test
	void getOrderByIdWhenOrderExistsTest() {
		when(orderRepository.findById(orderId, language)).thenReturn(Optional.of(orderWithoutTotal));
		when(orderImageRepository.loadImageForProductInOrder(orderWithoutTotal)).thenReturn(orderWithoutTotal);
		when(calculateOrderTotalPriceUseCase.calculateTotalPriceFor(orderWithoutTotal)).thenReturn(order);

		Order result = getOrderByIdUseCase.getOrderById(orderId, language);

		assertNotNull(result);
		assertEquals(order, result);
		verify(orderRepository).findById(orderId, language);
		verify(calculateOrderTotalPriceUseCase).calculateTotalPriceFor(orderWithoutTotal);
		verify(orderImageRepository).loadImageForProductInOrder(orderWithoutTotal);
	}

	@Test
	void getOrderByIdWhenOrderNotFoundTest() {
		when(orderRepository.findById(orderId, language)).thenReturn(Optional.empty());

		var exception = assertThrows(OrderNotFoundException.class,
				() -> getOrderByIdUseCase.getOrderById(orderId, language));

		assertEquals(orderId, exception.getOrderId());
		verify(orderRepository, times(1)).findById(orderId, language);
	}
}
