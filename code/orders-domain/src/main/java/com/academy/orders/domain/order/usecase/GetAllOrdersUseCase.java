package com.academy.orders.domain.order.usecase;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.order.dto.OrdersFilterParametersDto;
import com.academy.orders.domain.order.entity.Order;

public interface GetAllOrdersUseCase {
	Page<Order> getAllOrders(OrdersFilterParametersDto filterParametersDto, String language, Pageable pageable);
}
