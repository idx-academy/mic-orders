package com.academy.orders.domain.account.usecase;

public interface GetAccountDetailsUseCase {
	/**
	 * Retrieves the unique identifier of the account.
	 *
	 * @return the ID of the account.
	 */
	Long getId();

	/**
	 * Retrieves the first name associated with the account.
	 *
	 * @return the first name of the account holder.
	 */
	String getFirstName();

	/**
	 * Retrieves the last name associated with the account.
	 *
	 * @return the last name of the account holder.
	 */
	String getLastName();
}
