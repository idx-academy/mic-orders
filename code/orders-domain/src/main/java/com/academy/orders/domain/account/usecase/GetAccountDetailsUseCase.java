package com.academy.orders.domain.account.usecase;

/**
 * Use case interface for getting account details.
 */
public interface GetAccountDetailsUseCase {
	/**
	 * Retrieves the unique identifier of the account.
	 *
	 * @return the ID of the account.
	 *
	 * @author Denys Liubchenko
	 */
	Long getId();

	/**
	 * Retrieves the first name associated with the account.
	 *
	 * @return the first name of the account holder.
	 *
	 * @author Denys Liubchenko
	 */
	String getFirstName();

	/**
	 * Retrieves the last name associated with the account.
	 *
	 * @return the last name of the account holder.
	 *
	 * @author Denys Liubchenko
	 */
	String getLastName();
}
