package com.academy.orders.domain.order.usecase;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.order.dto.OrdersFilterParametersDto;
import com.academy.orders.domain.order.entity.Order;

public interface GetAllOrdersUseCase {
	/**
	 * Retrieves a paginated list of orders based on the specified filter parameters
	 * and language.
	 *
	 * @param filterParametersDto
	 *            the filter parameters to apply when retrieving the orders
	 * @param language
	 *            the language in which to retrieve the orders
	 * @param pageable
	 *            the pagination information
	 * @return a paginated list of orders that match the filter parameters and
	 *         language
	 */
	Page<Order> getAllOrders(OrdersFilterParametersDto filterParametersDto, String language, Pageable pageable);
}
