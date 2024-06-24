package com.academy.orders.domain.order.entity;

import java.util.UUID;

import lombok.Builder;

@Builder
public record Order(UUID id) {
}
