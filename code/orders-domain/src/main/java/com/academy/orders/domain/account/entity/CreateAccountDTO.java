package com.academy.orders.domain.account.entity;

import lombok.Builder;

@Builder
public record CreateAccountDTO(
    String password,
    String email,
    String firstName,
    String lastName
) {

}
