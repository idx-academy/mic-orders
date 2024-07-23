package com.academy.orders.domain.product.dto;

import lombok.Builder;

@Builder
public record LanguageDto(Long id, String code) {
}
