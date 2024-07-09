package com.academy.orders.domain.cart.dto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CartItemDto(UUID productId, String image, String name, BigDecimal productPrice, Integer quantity,
		BigDecimal calculatedPrice) {
}
