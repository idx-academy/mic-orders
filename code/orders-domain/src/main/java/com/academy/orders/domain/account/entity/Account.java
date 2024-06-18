package com.academy.orders.domain.account.entity;

import com.academy.orders.domain.account.entity.enumerated.Role;
import com.academy.orders.domain.account.entity.enumerated.UserStatus;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record Account(
    Long id,
    String password,
    String email,
    String firstName,
    String lastName,
    Role role,
    UserStatus status,
    LocalDateTime createdAt
) {

}
