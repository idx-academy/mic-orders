package com.academy.orders.domain.order.entity;

import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.Builder;

@Builder
public record Order(UUID id, Boolean isPaid, OrderStatus orderStatus, LocalDateTime createdAt, ReceiverInfo receiver,
		PostAddress postAddress, List<OrderItem> orderItems, BigDecimal total) {
}
