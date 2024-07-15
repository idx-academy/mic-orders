package com.academy.orders.application.order.usecase;

import com.academy.orders.application.ModelUtils;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.order.dto.OrdersFilterParametersDto;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.repository.OrderRepository;
import com.academy.orders.domain.order.usecase.CalculateOrderTotalPriceUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.academy.orders.application.ModelUtils.getOrder;
import static com.academy.orders.application.ModelUtils.getOrderWithoutTotal;
import static com.academy.orders.application.ModelUtils.getPageOf;
import static com.academy.orders.application.ModelUtils.getPageable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllOrdersUseCaseTest {
	@InjectMocks
	private GetAllOrdersUseCaseImpl getAllOrdersUseCase;
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private CalculateOrderTotalPriceUseCase calculateOrderTotalPriceUseCase;

	@Test
	void getOrdersByUserId() {
		// Given
		OrdersFilterParametersDto filterParametersDto = ModelUtils.getOrdersFilterParametersDto();
		String language = "ua";
		Pageable pageable = getPageable();
		Order withoutTotal = getOrderWithoutTotal();
		Order withTotal = getOrder();
		Page<Order> orderPage = getPageOf(withoutTotal);
		Page<Order> expected = getPageOf(withTotal);

		when(orderRepository.findAll(filterParametersDto, language, pageable)).thenReturn(orderPage);
		when(calculateOrderTotalPriceUseCase.calculateTotalPriceFor(orderPage.content()))
				.thenReturn(expected.content());

		// When
		Page<Order> ordersByUserId = getAllOrdersUseCase.getAllOrders(filterParametersDto, language, pageable);

		// Then
		assertEquals(expected, ordersByUserId);
		verify(orderRepository).findAll(filterParametersDto, language, pageable);
	}
}
