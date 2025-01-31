package com.academy.orders.domain.product.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateProductDiscountRequestDto(UUID productId, int amount, LocalDateTime startDate,
		LocalDateTime endDate) {
}
