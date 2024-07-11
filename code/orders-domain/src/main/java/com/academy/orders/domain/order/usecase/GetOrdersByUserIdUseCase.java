package com.academy.orders.domain.order.usecase;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.order.entity.Order;

public interface GetOrdersByUserIdUseCase {
	Page<Order> getOrdersByUserId(Long id, String language, Pageable pageable);
}
