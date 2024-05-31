package com.academy.orders.domain.usecase;

import java.io.Serial;
import java.util.UUID;

import lombok.Getter;

public class OrderNotFoundException extends RuntimeException {

	@Getter
	private final UUID orderId;

	@Serial
	private static final long serialVersionUID = 90712755442212371L;

	public OrderNotFoundException(UUID orderId) {
		super(String.format("Order %s is not found", orderId));
		this.orderId = orderId;
	}
}
