package com.academy.orders.domain.order.usecase;

import java.util.UUID;

import com.academy.orders.domain.order.entity.Order;

public interface GetOrderByIdUseCase {

	Order getOrderById(UUID id, String language);
}
