package com.academy.orders.domain.order.entity;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record OrderManagement(UUID id, OrderStatus orderStatus, List<String> availableStatuses, OrderReceiver receiver,
		PostAddress postAddress, BigDecimal total, Account account, List<OrderItem> orderItems, Boolean isPaid,
		LocalDateTime editedAt, LocalDateTime createdAt) {
}
