package com.academy.orders.domain.cart.exception;

import com.academy.orders.domain.exception.AlreadyExistsException;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CartItemAlreadyExistsException extends AlreadyExistsException {
	private final UUID productId;

	public CartItemAlreadyExistsException(UUID productId) {
		super(String.format("Product with %s id already added to the cart", productId));
		this.productId = productId;
	}
}
