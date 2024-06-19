package com.academy.orders.domain.exception;

public abstract class NotFoundException extends RuntimeException {
	public NotFoundException(String message) {
		super(message);
	}
}
