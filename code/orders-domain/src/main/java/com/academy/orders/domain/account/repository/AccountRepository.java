package com.academy.orders.domain.account.repository;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.entity.CreateAccountDTO;
import com.academy.orders.domain.account.entity.enumerated.UserStatus;
import java.util.Optional;

public interface AccountRepository {
	Optional<Account> getAccountByEmail(String email);
	Boolean existsByEmail(String email);
	Boolean existsById(Long id);
	Account save(CreateAccountDTO account);
	void updateStatus(Long id, UserStatus status);
}
