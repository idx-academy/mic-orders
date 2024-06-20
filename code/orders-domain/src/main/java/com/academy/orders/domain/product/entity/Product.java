package com.academy.orders.domain.product.entity;

import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record Product(UUID id, ProductStatus status, String image, LocalDateTime createdAt, List<Tag> tag, int quantity,
		BigDecimal price) {
}
