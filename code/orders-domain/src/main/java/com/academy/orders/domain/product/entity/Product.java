package com.academy.orders.domain.product.entity;

import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
public record Product(UUID id, ProductStatus status, String image, LocalDateTime createdAt, int quantity,
		BigDecimal price, Set<Tag> tags, Set<ProductTranslation> productTranslations) {
}
