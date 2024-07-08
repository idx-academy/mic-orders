package com.academy.orders.domain.cart.exception;

import com.academy.orders.domain.exception.NotFoundException;
import java.util.UUID;
import lombok.Getter;

@Getter
public class CartItemNotFoundException extends NotFoundException {
	private final UUID productId;

	public CartItemNotFoundException(UUID productId) {
		super(String.format("Cannot find product with id: %s in user's cart.", productId));
		this.productId = productId;
	}
}
