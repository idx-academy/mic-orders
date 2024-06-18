package com.academy.orders.domain.entity;

import com.academy.orders.domain.enumerated.Role;
import com.academy.orders.domain.enumerated.UserStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

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
