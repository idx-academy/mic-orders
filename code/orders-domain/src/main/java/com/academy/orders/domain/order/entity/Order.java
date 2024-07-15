package com.academy.orders.domain.order.entity;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record Order(UUID id, OrderStatus orderStatus, OrderReceiver receiver, PostAddress postAddress, BigDecimal total,
		Account account, List<OrderItem> orderItems, Boolean isPaid, LocalDateTime editedAt, LocalDateTime createdAt) {
}
