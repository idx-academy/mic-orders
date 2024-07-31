package com.academy.orders.domain.account.repository;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.entity.CreateAccountDTO;
import com.academy.orders.domain.account.entity.enumerated.Role;

import java.util.Optional;

public interface AccountRepository {
	/**
	 * Retrieves an {@link Account} entity by its email.
	 *
	 * @param email
	 *            the email address of the account to be retrieved.
	 * @return an {@link Optional} containing the {@link Account} entity if found,
	 *         otherwise empty.
	 *
	 * @author Denys Ryhal
	 */
	Optional<Account> getAccountByEmail(String email);

	/**
	 * Checks if an account exists by its email.
	 *
	 * @param email
	 *            the email address to check for existence.
	 * @return {@code true} if an account with the given email exists, otherwise
	 *         {@code false}.
	 *
	 * @author Denys Ryhal
	 */
	Boolean existsByEmail(String email);

	/**
	 * Saves a new {@link Account} entity.
	 *
	 * @param account
	 *            the {@link CreateAccountDTO} containing the information necessary
	 *            to create a new account.
	 * @return the saved {@link Account} entity.
	 *
	 * @author Denys Ryhal
	 */
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
}
