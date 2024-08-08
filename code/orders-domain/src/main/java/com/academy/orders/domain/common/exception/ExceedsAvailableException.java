package com.academy.orders.domain.common.exception;

public abstract class ExceedsAvailableException extends RuntimeException {
	protected ExceedsAvailableException(String message) {
		super(message);
	}
}
