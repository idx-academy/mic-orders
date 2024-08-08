package com.academy.orders.domain.order.usecase;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.order.entity.Order;

/**
 * Use case interface for getting orders history by user id.
 */
public interface GetOrdersByUserIdUseCase {
	/**
	 * Retrieves a paginated list of orders for a specific user based on their user
	 * ID and language preference.
	 *
	 * @param id
	 *            the ID of the user whose orders are to be retrieved.
	 * @param language
	 *            the language in which to retrieve the orders.
	 * @param pageable
	 *            the pagination information.
	 *
	 * @return a paginated list of orders for the specified user that match the
	 *         language preference.
	 */
	Page<Order> getOrdersByUserId(Long id, String language, Pageable pageable);
}
