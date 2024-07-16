package com.academy.orders.domain.cart.usecase;

import com.academy.orders.domain.cart.dto.UpdatedCartItemDto;

import java.util.UUID;

public interface SetCartItemQuantityUseCase {
	UpdatedCartItemDto setQuantity(UUID productId, Long userId, Integer quantity);
}
