package com.academy.orders.domain.common.exception;

public abstract class BadRequestException extends RuntimeException {
	protected BadRequestException(String message) {
		super(message);
	}
}
