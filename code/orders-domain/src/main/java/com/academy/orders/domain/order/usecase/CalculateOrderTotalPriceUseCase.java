package com.academy.orders.domain.order.usecase;

import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.OrderItem;
import java.math.BigDecimal;
import java.util.List;

public interface CalculateOrderTotalPriceUseCase {
	/**
	 * Calculates the total price for a list of order items.
	 *
	 * @param orderItems
	 *            the list of order items for which to calculate the total price
	 * @return the total price of all the order items combined
	 */
	BigDecimal calculateTotalPrice(List<OrderItem> orderItems);

	/**
	 * Calculates the total price for each order in a list of orders.
	 *
	 * @param orders
	 *            the list of orders for which to calculate the total price
	 * @return the list of orders with their total prices calculated
	 */
	List<Order> calculateTotalPriceFor(List<Order> orders);

	/**
	 * Calculates the total price for a single order.
	 *
	 * @param order
	 *            the order for which to calculate the total price
	 * @return the order with its total price calculated
	 */
	Order calculateTotalPriceFor(Order order);
}
