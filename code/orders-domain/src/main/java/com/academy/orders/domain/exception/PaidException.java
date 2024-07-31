package com.academy.orders.domain.exception;

public class PaidException extends RuntimeException {
	protected PaidException(String message) {
		super(message);
	}
}
