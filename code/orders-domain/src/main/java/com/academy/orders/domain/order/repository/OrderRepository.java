package com.academy.orders.domain.order.repository;

import java.util.Optional;
import java.util.UUID;

import com.academy.orders.domain.order.entity.Order;

public interface OrderRepository {
	/**
	 * Method find {@link Order} by order's id
	 *
	 * @param id
	 *            with type {@link UUID}
	 *
	 * @return {@link Optional} of {@link Order}
	 * @author Denys Ryhal
	 */
	Optional<Order> findById(UUID id);

	/**
	 * Method saves order to the DB.
	 *
	 * @param accountId
	 *            id of logged-in user
	 * @param order
	 *            to save
	 *
	 * @return {@link UUID} id of created order
	 * @author Denys Ryhal
	 */
	UUID save(Order order, Long accountId);
}
