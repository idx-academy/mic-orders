package com.academy.orders.domain.exception;

public abstract class NotFoundException extends RuntimeException {
	protected NotFoundException(String message) {
		super(message);
	}
}