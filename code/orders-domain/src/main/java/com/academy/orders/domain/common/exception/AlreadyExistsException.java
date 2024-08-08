package com.academy.orders.domain.common.exception;

public abstract class AlreadyExistsException extends RuntimeException {
	protected AlreadyExistsException(String message) {
		super(message);
	}
}