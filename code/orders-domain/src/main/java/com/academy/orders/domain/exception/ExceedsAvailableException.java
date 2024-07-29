package com.academy.orders.domain.exception;

public abstract class ExceedsAvailableException extends RuntimeException {
	protected ExceedsAvailableException(String message) {
		super(message);
	}
}
