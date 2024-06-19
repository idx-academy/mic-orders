package com.academy.orders.domain.account.repository;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.entity.CreateAccountDTO;
import java.util.Optional;

public interface AccountRepository {
	Optional<Account> getAccountByEmail(String email);
	Account save(CreateAccountDTO account);
}
