package com.academy.orders.domain.order.repository;

import com.academy.orders.domain.order.entity.Order;

public interface OrderImageRepository {
	Order loadImageForProductInOrder(Order order);
}
