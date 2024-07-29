package com.academy.orders.domain.order.usecase;

import com.academy.orders.domain.order.entity.enumerated.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface UpdateOrderStatusUseCase {
	/**
	 * Updates the status of an order identified by its ID.
	 *
	 * @param orderId
	 *            the unique identifier of the order to be updated.
	 * @param newStatus
	 *            the new status to be set for the order.
	 * @param currentAccountEmail
	 *            the email of the current user performing the update.
	 * @return a list of available statuses that the order can transition to from
	 *         its new state.
	 * @author Anton Bondar
	 */
	List<String> updateOrderStatus(UUID orderId, OrderStatus newStatus, String currentAccountEmail);
}
