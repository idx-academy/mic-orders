package com.academy.orders.domain.repository;

import java.util.Optional;
import java.util.UUID;

import com.academy.orders.domain.entity.Order;

public interface OrderRepository {

	Optional<Order> findById(UUID id);
}
