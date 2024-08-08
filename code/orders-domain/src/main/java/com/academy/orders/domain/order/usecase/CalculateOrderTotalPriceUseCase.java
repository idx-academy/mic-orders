package com.academy.orders.domain.order.usecase;

import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.OrderItem;
import com.academy.orders.domain.order.entity.OrderManagement;

import java.math.BigDecimal;
import java.util.List;

/**
 * Use case interface for calculating total price.
 */
public interface CalculateOrderTotalPriceUseCase {
	/**
	 * Calculates the total price for a list of order items.
	 *
	 * @param orderItems
	 *            the list of order items for which to calculate the total price
	 *
	 * @return the total price of all the order items combined
	 *
	 * @author Denys Liubchenko
	 */
	BigDecimal calculateTotalPrice(List<OrderItem> orderItems);

	/**
	 * Calculates the total price for each order in a list of orders.
	 *
	 * @param orders
	 *            the list of orders for which to calculate the total price
	 *
	 * @return the list of orders with their total prices calculated
	 *
	 * @author Denys Liubchenko
	 */
	List<Order> calculateTotalPriceFor(List<Order> orders);

	/**
	 * Calculates the total price for a single order.
	 *
	 * @param order
	 *            the order for which to calculate the total price
	 *
	 * @return the order with its total price calculated
	 *
	 * @author Denys Liubchenko
	 */
	Order calculateTotalPriceFor(Order order);

	/**
	 * Calculates the total price for each order in a list of orders and determines
	 * the available statuses based on the user's role.
	 *
	 * @param orders
	 *            the list of orders for which to calculate the total price
	 * @param role
	 *            the role of the current user.
	 *
	 * @return the list of orders with their total prices calculated and available
	 *         statuses to change
	 *
	 * @author Denys Liubchenko
	 */
	List<OrderManagement> calculateTotalPriceAndAvailableStatuses(List<Order> orders, String role);
}
