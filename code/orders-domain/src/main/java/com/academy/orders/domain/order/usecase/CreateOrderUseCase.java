package com.academy.orders.domain.order.usecase;

import com.academy.orders.domain.order.dto.CreateOrderDto;
import java.util.UUID;

public interface CreateOrderUseCase {
	UUID createOrder(CreateOrderDto order, String userEmail);
}
