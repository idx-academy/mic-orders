package com.academy.orders.domain.product.entity;

import lombok.Builder;

@Builder
public record Tag(Long id, String name) {
}
