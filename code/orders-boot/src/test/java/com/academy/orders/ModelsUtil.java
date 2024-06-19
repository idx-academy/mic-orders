package com.academy.orders;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.entity.enumerated.Role;
import com.academy.orders.domain.account.entity.enumerated.UserStatus;
import java.time.LocalDateTime;

public class ModelsUtil {
    public static Account getAccount() {
        return Account.builder()
            .id(1L)
            .email("user@mail.com")
            .firstName("first")
            .lastName("last")
            .password("$2a$12$j6tAmpJpMhU6ATtgRIS0puHsPVxs2upwoBUbTtakSt9tlZ6uZ04IC")
            .role(Role.ROLE_ADMIN)
            .status(UserStatus.ACTIVE)
            .createdAt(LocalDateTime.now())
            .build();
    }
}
