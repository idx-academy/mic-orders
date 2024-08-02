package com.academy.orders.domain.product.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record UpdateProductDto(UUID id, String name, String description, String status, String image, Integer quantity,
		BigDecimal price, List<Long> tagIds, LocalDateTime createdAt) {
}
