package com.academy.orders.domain.product.dto;

import lombok.Builder;

@Builder
public record ProductTranslationDto(String name, String description, String languageCode) {
}
