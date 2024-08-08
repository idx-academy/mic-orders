package com.academy.orders.domain.cart.exception;

import com.academy.orders.domain.common.exception.ExceedsAvailableException;
import lombok.Getter;
import java.util.UUID;

@Getter
public class QuantityExceedsAvailableException extends ExceedsAvailableException {
	private final UUID product;
	private final Integer quantity;
	private final Integer stockQuantity;

	public QuantityExceedsAvailableException(UUID product, Integer quantity, Integer stockQuantity) {
		super(String.format("Product with id: %s exceeded available quantity. Requested: %d, Available: %d", product,
				quantity, stockQuantity));
		this.product = product;
		this.quantity = quantity;
		this.stockQuantity = stockQuantity;
	}
}
