package com.academy.orders.domain.exception;

public abstract class QuantityExceedsAvailableException extends RuntimeException {
	protected QuantityExceedsAvailableException(String message) {
		super(message);
	}
}
