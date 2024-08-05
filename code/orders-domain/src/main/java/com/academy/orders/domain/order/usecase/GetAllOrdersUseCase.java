package com.academy.orders.domain.order.usecase;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.order.dto.OrdersFilterParametersDto;
import com.academy.orders.domain.order.entity.OrderManagement;

public interface GetAllOrdersUseCase {
	/**
	 * Retrieves a paginated list of orders based on the specified filter parameters
	 * and language.
	 *
	 * @param filterParametersDto
	 *            the filter parameters to apply when retrieving the orders
	 * @param pageable
	 *            the pagination information
	 * @param role
	 *            the role of the current user
	 * @return a paginated list of orders that match the filter parameters and
	 *         language
	 */
	Page<OrderManagement> getAllOrders(OrdersFilterParametersDto filterParametersDto, Pageable pageable, String role);
}
