package com.academy.orders.domain.account.usecase;

import com.academy.orders.domain.account.dto.AccountManagementFilterDto;
import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;

/**
 * Use case interface for getting all accounts.
 */
public interface GetAllUsersUseCase {
	/**
	 * Retrieves a paginated list of accounts based on the provided filter criteria.
	 *
	 * @param filter
	 *            the {@link AccountManagementFilterDto} containing filtering
	 *            criteria such as role, status, etc.
	 * @param pageable
	 *            the {@link Pageable} object for pagination and sorting
	 *            information.
	 * @return a {@link Page} of {@link Account} objects that match the filter
	 *         criteria.
	 *
	 * @author Yurii Osovskyi
	 */
	Page<Account> getAllUsers(AccountManagementFilterDto filter, Pageable pageable);
}
