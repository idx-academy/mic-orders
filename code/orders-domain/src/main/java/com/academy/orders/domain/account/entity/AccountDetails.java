package com.academy.orders.domain.account.entity;

import lombok.Builder;

@Builder
public record AccountDetails(Long id, String firstName, String lastName) {
}
