package com.academy.orders.domain.product.entity;

import lombok.Builder;
import java.util.UUID;

@Builder
public record ProductTranslationManagement(UUID productId, Long languageId, String name, String description,
		Language language) {
}
