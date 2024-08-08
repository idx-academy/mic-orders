package com.academy.orders.domain.order.exception;

import com.academy.orders.domain.common.exception.BadRequestException;
import java.util.UUID;
import lombok.Getter;

@Getter
public class InsufficientProductQuantityException extends BadRequestException {
	private final UUID productId;

	public InsufficientProductQuantityException(UUID productId) {
		super("Ordered quantity exceeds available stock for product: " + productId);
		this.productId = productId;
	}
}
