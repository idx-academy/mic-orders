package com.academy.orders.domain.order.usecase;

import com.academy.orders.domain.order.dto.CreateOrderDto;
import java.util.UUID;

public interface CreateOrderUseCase {
	/**
	 * Method creates new order.
	 *
	 * @param order     {@link CreateOrderDto}
	 * @param accountId id of the user.
	 * @author Denys Ryhal
	 */
	UUID createOrder(CreateOrderDto order, Long accountId);
}
