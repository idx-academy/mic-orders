package com.academy.orders.domain.cart.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;

@Builder
public record CartResponseDto(List<CartItemDto> items, BigDecimal totalPrice) {
}
