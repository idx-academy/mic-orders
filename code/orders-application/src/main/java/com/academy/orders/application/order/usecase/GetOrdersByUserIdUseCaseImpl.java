package com.academy.orders.application.order.usecase;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.repository.OrderImageRepository;
import com.academy.orders.domain.order.repository.OrderRepository;
import com.academy.orders.domain.order.usecase.CalculateOrderTotalPriceUseCase;
import com.academy.orders.domain.order.usecase.GetOrdersByUserIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetOrdersByUserIdUseCaseImpl implements GetOrdersByUserIdUseCase {
	private final OrderRepository orderRepository;
	private final CalculateOrderTotalPriceUseCase calculateOrderTotalPriceUseCase;
	private final OrderImageRepository orderImageRepository;

	@Override
	public Page<Order> getOrdersByUserId(Long id, String language, Pageable pageable) {
		Page<Order> orderPage = orderRepository.findAllByUserId(id, language, pageable);
		Page<Order> newPage = buildPage(orderPage);

		return newPage.map(orderImageRepository::loadImageForProductInOrder);
	}

	private Page<Order> buildPage(Page<Order> orderPage) {
		return Page.<Order>builder().totalElements(orderPage.totalElements()).totalPages(orderPage.totalPages())
				.first(orderPage.first()).last(orderPage.last()).number(orderPage.number())
				.numberOfElements(orderPage.numberOfElements()).size(orderPage.size()).empty(orderPage.empty())
				.content(calculateOrderTotalPriceUseCase.calculateTotalPriceFor(orderPage.content())).build();
	}
}
