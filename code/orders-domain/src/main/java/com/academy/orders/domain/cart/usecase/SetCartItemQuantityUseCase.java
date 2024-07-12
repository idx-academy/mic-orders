package com.academy.orders.domain.cart.usecase;

import java.util.UUID;

public interface SetCartItemQuantityUseCase {
	void setQuantity(UUID productId, Long userId, Integer quantity);
}
