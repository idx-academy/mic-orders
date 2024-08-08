package com.academy.orders.domain.account.usecase;

/**
 * Use case interface for checking if user`s id and id.
 */
public interface CheckAccountIdUseCase {
	/**
	 * Checks if user`s id and id in the principal are the same
	 *
	 * @param id
	 *            id of user
	 *
	 * @author Denys Liubchenko
	 *
	 **/
	boolean hasSameId(Long id);
}
