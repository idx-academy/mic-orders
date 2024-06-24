package com.academy.orders.domain.order.repository;

import java.util.Optional;
import java.util.UUID;

import com.academy.orders.domain.order.entity.Order;

public interface OrderRepository {

	Optional<Order> findById(UUID id);
}
