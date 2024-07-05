package com.academy.orders.domain.cart.usecase;

import java.util.UUID;

public interface DeleteProductFromCartUseCase {
	void deleteProductFromCart(Long userId, UUID productId);
}
