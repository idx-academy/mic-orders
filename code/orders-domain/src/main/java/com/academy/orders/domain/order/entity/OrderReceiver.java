package com.academy.orders.domain.order.entity;

import lombok.Builder;

@Builder
public record OrderReceiver(String firstName, String lastName, String email) {
}
