package com.academy.orders.domain.product.dto;

import com.academy.orders.domain.product.entity.ProductTranslationManagement;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
public record ProductResponse(UUID productId, String status, String image, LocalDateTime createdAt, Integer quantity,
		BigDecimal price, Set<TagDto> tags, Set<ProductTranslationManagement> productTranslations) {
}
