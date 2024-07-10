package com.academy.orders.domain.order.repository;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import java.util.Optional;
import java.util.UUID;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.enumerated.OrderStatus;

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

	Page<Order> findAllByUserId(Long userId, String language, Pageable pageable);

	/**
	 * Updates the status of an order identified by its ID.
	 *
	 * @param orderId
	 *            the unique identifier of the order to be updated.
	 * @param orderStatus
	 *            the new status to be set for the order.
	 * @author Anton Bondar
	 */
	void updateOrderStatus(UUID orderId, OrderStatus orderStatus);
}
