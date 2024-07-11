package com.academy.orders.domain.order.usecase;

import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import java.util.UUID;

public interface UpdateOrderStatusUseCase {
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
