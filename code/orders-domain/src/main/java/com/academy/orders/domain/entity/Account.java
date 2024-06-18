package com.academy.orders.domain.entity;

import com.academy.orders.domain.enumerated.Role;
import com.academy.orders.domain.enumerated.UserStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Account {
    private Long id;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private UserStatus status;
    private LocalDateTime createdAt;
}
