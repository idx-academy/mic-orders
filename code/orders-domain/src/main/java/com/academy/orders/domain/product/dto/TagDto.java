package com.academy.orders.domain.product.dto;

import lombok.Builder;

@Builder
public record TagDto(Long id, String name) {
}
