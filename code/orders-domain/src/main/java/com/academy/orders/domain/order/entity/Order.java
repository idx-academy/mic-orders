package com.academy.orders.domain.order.entity;

import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.Builder;

@Builder
public record Order(
    UUID id,
    OrderStatus status,
    OrderReceiver receiver,
    PostAddress postAddress,
    List<OrderItem> items,
    Boolean isPaid,
    LocalDateTime createdAt
    ) {
}
