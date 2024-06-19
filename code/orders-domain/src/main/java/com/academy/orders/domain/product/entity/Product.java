package com.academy.orders.domain.product.entity;

import lombok.Builder;

import java.util.UUID;

@Builder
public record Product(UUID id) {
}
