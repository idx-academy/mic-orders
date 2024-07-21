package com.academy.orders.domain.product.entity;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
public record UpdateProduct(UUID id, String name, String description, String status, String image, int quantity,
		BigDecimal price, Set<Tag> tags, LocalDateTime createdAt) {
}
