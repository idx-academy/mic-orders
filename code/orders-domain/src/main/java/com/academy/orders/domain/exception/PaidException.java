package com.academy.orders.domain.exception;

public abstract class PaidException extends RuntimeException {
	protected PaidException(String message) {
		super(message);
	}
}
