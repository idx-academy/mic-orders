package com.academy.orders.domain.order.usecase;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.order.dto.OrderFilterParametersDto;
import com.academy.orders.domain.order.entity.Order;

public interface GetAllOrdersUseCase {
	Page<Order> getAllOrders(OrderFilterParametersDto filterParametersDto, String language, Pageable pageable);
}
