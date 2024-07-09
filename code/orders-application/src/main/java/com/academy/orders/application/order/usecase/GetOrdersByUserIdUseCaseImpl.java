package com.academy.orders.application.order.usecase;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.repository.OrderRepository;
import com.academy.orders.domain.order.usecase.CalculateOrderTotalPriceUseCase;
import com.academy.orders.domain.product.usecase.GetOrdersByUserIdUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetOrdersByUserIdUseCaseImpl implements GetOrdersByUserIdUseCase {
	private final OrderRepository orderRepository;
	private final CalculateOrderTotalPriceUseCase calculateOrderTotalPriceUseCase;
	@Override
	public Page<Order> getOrdersByUserId(Long id, String language, Pageable pageable) {
		Page<Order> orderPage = orderRepository.findAllByUserId(id, language, pageable);
		List<Order> orderList = orderPage.content().stream()
				.map(order -> Order.builder().id(order.id()).orderStatus(order.orderStatus()).receiver(order.receiver())
						.postAddress(order.postAddress())
						.total(calculateOrderTotalPriceUseCase.calculateTotalPrice(order.orderItems()))
						.orderItems(order.orderItems()).isPaid(order.isPaid()).editedAt(order.editedAt())
						.createdAt(order.createdAt()).build())
				.toList();
		return Page.<Order>builder().totalElements(orderPage.totalElements()).totalPages(orderPage.totalPages())
				.first(orderPage.first()).last(orderPage.last()).number(orderPage.number())
				.numberOfElements(orderPage.numberOfElements()).size(orderPage.size()).empty(orderPage.empty())
				.content(orderList).build();
	}
}
