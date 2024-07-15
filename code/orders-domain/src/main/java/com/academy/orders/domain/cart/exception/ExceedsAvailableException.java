package com.academy.orders.domain.cart.exception;

import com.academy.orders.domain.exception.QuantityExceedsAvailableException;
import lombok.Getter;
import java.util.UUID;

@Getter
public class ExceedsAvailableException extends QuantityExceedsAvailableException {
	private final UUID product;
	private final Integer quantity;

	public ExceedsAvailableException(UUID product, Integer quantity) {
		super(String.format("Product with id: %s exceeded available quantity", product));
		this.product = product;
		this.quantity = quantity;
	}
}
