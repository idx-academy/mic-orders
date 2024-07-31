package com.academy.orders.domain.account.repository;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.entity.CreateAccountDTO;
import com.academy.orders.domain.account.entity.enumerated.UserStatus;
import com.academy.orders.domain.account.entity.enumerated.Role;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import java.util.Optional;

public interface AccountRepository {
	Optional<Account> getAccountByEmail(String email);
	Boolean existsByEmail(String email);
	Account save(CreateAccountDTO account);

	/**
	 * Retrieves a {@link Role} entity associated with an account by its email.
	 *
	 * @param email
	 *            the email address of the account whose role is to be retrieved.
	 * @return an {@link Optional} containing the {@link Role} entity if found,
	 *         otherwise empty.
	 *
	 * @author Anton Bondar
	 */
	Optional<Role> findRoleByEmail(String email);

	Boolean existsById(Long id);
	void updateStatus(Long id, UserStatus status);
	Page<Account> getAccounts(Pageable pageable);
}
