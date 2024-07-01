package com.academy.orders.domain.order.usecase;

import com.academy.orders.domain.order.dto.CreateOrderDto;
import java.util.UUID;

public interface CreateOrderUseCase {
	/**
	 * Method creates new order.
	 *
	 * @param order {@link CreateOrderDto}
	 * @param userEmail email of the user.
	 *
	 * @author Denys Ryhal
	 */
	UUID createOrder(CreateOrderDto order, String userEmail);
}
