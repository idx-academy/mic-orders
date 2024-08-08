package com.academy.orders.domain.account.exception;

import com.academy.orders.domain.common.exception.AlreadyExistsException;
import lombok.Getter;

public class AccountAlreadyExistsException extends AlreadyExistsException {
	@Getter
	private final String email;
	public AccountAlreadyExistsException(String email) {
		super(String.format("Account with email %s already exists", email));
		this.email = email;
	}
}
