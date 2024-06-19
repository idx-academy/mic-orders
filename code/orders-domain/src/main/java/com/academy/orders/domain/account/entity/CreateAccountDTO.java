package com.academy.orders.domain.account.entity;

import lombok.Builder;

@Builder
public record CreateAccountDTO(String email, String password, String firstName, String lastName) {

}
