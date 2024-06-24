package com.academy.orders.domain.exception;

public abstract class AlreadyExistsException extends RuntimeException {
	protected AlreadyExistsException(String message) {
		super(message);
	}
}