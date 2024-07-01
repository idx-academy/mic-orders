package com.academy.orders.domain.account.exception;

import com.academy.orders.domain.exception.NotFoundException;
import lombok.Getter;

@Getter
public class AccountNotFoundException extends NotFoundException {
	private final Long accountId;

	public AccountNotFoundException(Long accountId) {
		super(String.format("Account with id: %d is not found", accountId));
		this.accountId = accountId;
	}
}
