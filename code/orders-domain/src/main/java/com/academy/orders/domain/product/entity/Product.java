package com.academy.orders.domain.product.entity;

import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record Product(UUID id, ProductStatus status, String image, LocalDateTime createdAt, int quantity,
		BigDecimal price, Set<Tag> tags, Set<ProductTranslation> productTranslations) {
}
