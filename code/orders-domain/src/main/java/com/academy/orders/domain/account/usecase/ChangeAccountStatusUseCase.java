package com.academy.orders.domain.account.usecase;

import com.academy.orders.domain.account.entity.enumerated.UserStatus;

public interface ChangeAccountStatusUseCase {
	/**
	 * Changes status of account
	 *
	 * @param id
	 *            id of the account
	 * @param status
	 *            new status of account
	 *
	 * @author Denys Ryhal
	 **/
	void changeStatus(Long id, UserStatus status);
}