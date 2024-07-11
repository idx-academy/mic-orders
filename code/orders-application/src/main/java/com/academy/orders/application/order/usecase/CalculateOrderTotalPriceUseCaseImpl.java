package com.academy.orders.application.order.usecase;

import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.OrderItem;
import com.academy.orders.domain.order.usecase.CalculateOrderTotalPriceUseCase;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalculateOrderTotalPriceUseCaseImpl implements CalculateOrderTotalPriceUseCase {
	@Override
	public BigDecimal calculateTotalPrice(List<OrderItem> orderItems) {
		return orderItems.stream().map(OrderItem::price).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Override
	public List<Order> calculateTotalPriceFor(List<Order> orders) {
		if (orders == null) {
			return null;
		}
		return orders.stream()
				.map(order -> Order.builder().id(order.id()).orderStatus(order.orderStatus()).receiver(order.receiver())
						.postAddress(order.postAddress()).total(calculateTotalPrice(order.orderItems()))
						.orderItems(order.orderItems()).isPaid(order.isPaid()).editedAt(order.editedAt())
						.account(order.account()).createdAt(order.createdAt()).build())
				.toList();
	}

	@Override
	public Order calculateTotalPriceFor(Order order) {
		return calculateTotalPriceFor(List.of(order)).get(0);
	}
}
