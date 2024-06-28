package com.academy.orders.domain.order.repository;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import java.util.Optional;
import java.util.UUID;

import com.academy.orders.domain.order.entity.Order;

public interface OrderRepository {

	Optional<Order> findById(UUID id);
	Page<Order> findAllByUserId(Long userId, String language, Pageable pageable);
}
