package com.academy.orders.domain.order.exception;

import java.util.UUID;
import lombok.Getter;

@Getter
public class InsufficientProductQuantityException extends RuntimeException {
	private final UUID productId;

	public InsufficientProductQuantityException(UUID productId) {
		super("Ordered quantity exceeds available stock for product: " + productId);
		this.productId = productId;
	}
}
