package com.academy.orders.domain.cart_item.exception;

import com.academy.orders.domain.exception.AlreadyExistsException;
import lombok.Getter;

import java.util.UUID;

public class CartItemAlreadyExistsException extends AlreadyExistsException {
	@Getter
	private final UUID productId;
	public CartItemAlreadyExistsException(UUID productId) {
		super(String.format("Product with %s id already added to the cart", productId));
		this.productId = productId;
	}
}
