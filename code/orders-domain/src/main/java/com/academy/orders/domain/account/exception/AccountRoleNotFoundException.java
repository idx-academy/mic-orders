package com.academy.orders.domain.account.exception;

import com.academy.orders.domain.common.exception.NotFoundException;
import lombok.Getter;

@Getter
public class AccountRoleNotFoundException extends NotFoundException {
	private final String accountEmail;

	public AccountRoleNotFoundException(String accountEmail) {
		super(String.format("The Role of account with email: %s is not found", accountEmail));
		this.accountEmail = accountEmail;
	}
}
