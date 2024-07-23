package com.academy.orders.domain.product.dto;

import lombok.Builder;
import java.util.UUID;

@Builder
public record ProductTranslationDto(UUID productId, Long languageId, String name, String description) {
}
