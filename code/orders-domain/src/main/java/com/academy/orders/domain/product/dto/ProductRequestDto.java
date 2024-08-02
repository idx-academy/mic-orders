package com.academy.orders.domain.product.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Builder
public record ProductRequestDto(String status, String image, Integer quantity, BigDecimal price, List<Long> tagIds,
		Set<ProductTranslationDto> productTranslations) {
}
