package com.academy.orders.domain.cart.dto;

import lombok.Builder;

@Builder
public record QuantityCheckResponseDto(boolean isAvailable, int availableQuantity) {
}
