package com.academy.orders.domain.cart.dto;

import lombok.Builder;
import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record UpdatedCartItemDto(UUID productId, Integer quantity, BigDecimal cartItemPrice, BigDecimal totalPrice) {
}
