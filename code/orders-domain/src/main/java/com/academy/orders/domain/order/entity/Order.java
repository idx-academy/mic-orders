package com.academy.orders.domain.order.entity;

import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.Builder;
import lombok.With;

@Builder
public record Order(UUID id, OrderStatus orderStatus, OrderReceiver receiver, PostAddress postAddress,
		@With List<OrderItem> orderItems, Boolean isPaid, LocalDateTime createdAt) {

	public Order addOrderItems(List<OrderItem> newItems) {
		var updatedItems = new ArrayList<>(Optional.ofNullable(this.orderItems).orElseGet(ArrayList::new));
		updatedItems.addAll(newItems);
		return this.withOrderItems(updatedItems);
	}
}
