package com.academy.orders.domain.order.dto;

import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import lombok.Builder;

@Builder
public record UpdateOrderStatusDto(OrderStatus status, Boolean isPaid) {
}
