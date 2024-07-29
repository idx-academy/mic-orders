package com.academy.orders.domain.exception;

public abstract class BadRequestException extends RuntimeException {
	protected BadRequestException(String message) {
		super(message);
	}
}
