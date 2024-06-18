package com.academy.orders.domain.account.usecase;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.entity.CreateAccountDTO;

public interface CreateUserAccountUseCase {
    void create(CreateAccountDTO account);
}
