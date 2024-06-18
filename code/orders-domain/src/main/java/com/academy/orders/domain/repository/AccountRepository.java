package com.academy.orders.domain.repository;

import com.academy.orders.domain.entity.Account;

public interface AccountRepository {
    Account getAccountByEmail(String email);
    Account save(Account account);
}
