package com.academy.orders.domain.common.exception;

public abstract class PaidException extends RuntimeException {
	protected PaidException(String message) {
		super(message);
	}
}
