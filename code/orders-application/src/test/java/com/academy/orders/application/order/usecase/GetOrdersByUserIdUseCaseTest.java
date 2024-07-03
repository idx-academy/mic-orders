package com.academy.orders.application.order.usecase;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.academy.orders.application.ModelUtils.getOrder;
import static com.academy.orders.application.ModelUtils.getPageOf;
import static com.academy.orders.application.ModelUtils.getPageable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetOrdersByUserIdUseCaseTest {
	@InjectMocks
	private GetOrdersByUserIdUseCaseImpl getOrdersByUserIdUseCase;
	@Mock
	private OrderRepository orderRepository;

	@Test
	void getOrdersByUserId() {
		// Given
		Long userId = 1L;
		String language = "ua";
		Pageable pageable = getPageable();
		Page<Order> expected = getPageOf(getOrder());

		when(orderRepository.findAllByUserId(userId, language, pageable)).thenReturn(expected);

		// When
		Page<Order> ordersByUserId = getOrdersByUserIdUseCase.getOrdersByUserId(userId, language, pageable);

		// Then
		assertEquals(expected, ordersByUserId);
		verify(orderRepository).findAllByUserId(userId, language, pageable);
	}
}
