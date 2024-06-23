package com.academy.orders.domain.product.entity;

import lombok.Builder;

@Builder
public record Language(Long id, String code) {
}
