package com.academy.orders.domain.cart_item.repository;

import com.academy.orders.domain.cart_item.entity.CartItem;
import com.academy.orders.domain.cart_item.entity.CreateCartItemDTO;

import java.util.UUID;

public interface CartItemRepository {

	Boolean existsByProductIdAndUserId(UUID productId, Long userId);
	CartItem save(CreateCartItemDTO cartItem);
}
