package com.academy.orders.domain.product.entity;

import lombok.Builder;

@Builder
public record ProductTranslation(String name, String description, Language language) {
}
