package com.academy.orders.domain.product.dto;

import com.academy.orders.domain.product.entity.Tag;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
public record CreateProductRequestDto(UUID productId, String status, String image, LocalDateTime createdAt,
		Integer quantity, BigDecimal price, Set<Tag> tags, Set<ProductTranslationDto> productTranslations) {
}
