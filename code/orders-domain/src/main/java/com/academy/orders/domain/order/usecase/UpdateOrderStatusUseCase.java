package com.academy.orders.domain.order.usecase;

import com.academy.orders.domain.order.dto.OrderStatusInfo;
import com.academy.orders.domain.order.dto.UpdateOrderStatusDto;
import java.util.UUID;

public interface UpdateOrderStatusUseCase {
	/**
	 * Updates the status of an order identified by its ID.
	 *
	 * @param orderId
	 *            the unique identifier of the order to be updated.
	 * @param updateOrderStatus
	 *            the DTO containing the new status and payment information to be
	 *            set for the order.
	 * @param currentAccountEmail
	 *            the email of the current user performing the update.
	 * @return an {@link OrderStatusInfo} object containing a list of available
	 *         statuses that the order can transition to from its new state and the
	 *         payment status of the order.
	 * @author Anton Bondar
	 */
	OrderStatusInfo updateOrderStatus(UUID orderId, UpdateOrderStatusDto updateOrderStatus, String currentAccountEmail);
}
