package com.academy.orders.domain.account.dto;

import com.academy.orders.domain.account.entity.enumerated.Role;
import com.academy.orders.domain.account.entity.enumerated.UserStatus;
import lombok.Builder;

@Builder
public record AccountManagementFilterDto(UserStatus status, Role role) {
}
