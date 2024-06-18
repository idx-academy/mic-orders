package com.academy.orders.domain.account.repository;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.entity.CreateAccountDTO;

public interface AccountRepository {
    Account getAccountByEmail(String email);
    Account save(CreateAccountDTO account);
}
