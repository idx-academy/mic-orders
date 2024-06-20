package com.academy.orders.domain.exception;

public abstract class AlreadyExistsException extends RuntimeException {
	public AlreadyExistsException(String message) {
		super(message);
	}
}