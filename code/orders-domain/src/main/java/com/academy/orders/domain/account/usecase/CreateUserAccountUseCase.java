package com.academy.orders.domain.account.usecase;

import com.academy.orders.domain.account.entity.CreateAccountDTO;
import com.academy.orders.domain.account.exception.AccountAlreadyExistsException;

/**
 * Use case interface for creating new account.
 */
public interface CreateUserAccountUseCase {
	/**
	 * Creates a new account based on the provided account details.
	 *
	 * @param account
	 *            the {@link CreateAccountDTO} object containing information about
	 *            the new account.
	 * @throws AccountAlreadyExistsException
	 *             if the provided account's email already exists.
	 *
	 * @author Denys Ryhal
	 */
	void create(CreateAccountDTO account);
}
