package com.academy.orders.domain.cart_item.entity;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateCartItemDTO(UUID productId, Long userId, Integer quantity) {
}
