package com.academy.orders.domain.usecase;

import java.util.UUID;

import com.academy.orders.domain.entity.Order;

public interface GetOrderByIdUseCase {

	Order getOrderById(UUID id);
}
