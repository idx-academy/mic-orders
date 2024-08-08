package com.academy.orders.domain.order.usecase;

import com.academy.orders.domain.order.exception.OrderNotFoundException;
import java.util.UUID;

import com.academy.orders.domain.order.entity.Order;

/**
 * Use case interface for getting order by his id.
 */
public interface GetOrderByIdUseCase {
	/**
	 * Retrieves an order by its ID with details localized to the specified
	 * language.
	 *
	 * @param id
	 *            the UUID of the order to be retrieved.
	 * @param language
	 *            the language code for localization of the order details.
	 *
	 * @return the {@link Order} with localized details if found.
	 *
	 * @throws OrderNotFoundException
	 *             if no order with the specified ID is found.
	 *
	 * @author Denys Liubchenko
	 */
	Order getOrderById(UUID id, String language);
}
