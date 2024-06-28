package com.academy.orders.domain.cart_item.usecase;

import com.academy.orders.domain.cart_item.entity.CreateCartItemDTO;

public interface CreateCartItemByUser {

	void create(CreateCartItemDTO cartItem);
}
