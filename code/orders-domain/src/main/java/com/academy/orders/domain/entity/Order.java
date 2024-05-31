package com.academy.orders.domain.entity;

import java.util.UUID;

import lombok.Builder;

@Builder
public record Order(UUID id) {
}
