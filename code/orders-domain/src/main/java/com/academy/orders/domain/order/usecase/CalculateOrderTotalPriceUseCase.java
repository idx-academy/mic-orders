package com.academy.orders.domain.order.usecase;

import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.OrderItem;
import java.math.BigDecimal;
import java.util.List;

public interface CalculateOrderTotalPriceUseCase {
	BigDecimal calculateTotalPrice(List<OrderItem> orderItems);
	List<Order> calculateTotalPriceFor(List<Order> orders);
	Order calculateTotalPriceFor(Order order);
}
