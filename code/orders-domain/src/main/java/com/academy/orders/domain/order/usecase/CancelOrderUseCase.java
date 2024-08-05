package com.academy.orders.domain.order.usecase;

import com.academy.orders.domain.order.exception.OrderFinalStateException;
import com.academy.orders.domain.order.exception.OrderNotFoundException;
import java.util.UUID;

public interface CancelOrderUseCase {
	/**
	 * Cancels the order for a specific user.
	 *
	 * @param userId
	 *            the ID of the user who placed the order
	 * @param orderId
	 *            the unique identifier of the order to be canceled
	 *
	 * @throws OrderNotFoundException
	 *             if the order does not exist
	 * @throws OrderFinalStateException
	 *             if the user is not authorized to cancel the order
	 *
	 * @author Denys Liubchenko
	 */
	void cancelOrder(Long userId, UUID orderId);
}
