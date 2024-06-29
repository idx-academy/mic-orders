package com.academy.orders.domain.cart.repository;

import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.cart.entity.CreateCartItemDTO;

import java.util.UUID;

public interface CartItemRepository {

	Boolean existsByProductIdAndUserId(UUID productId, Long userId);
	CartItem save(CreateCartItemDTO cartItem);
}
