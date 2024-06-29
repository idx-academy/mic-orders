package com.academy.orders.domain.cart.usecase;

import com.academy.orders.domain.cart.entity.CreateCartItemDTO;

public interface CreateCartItemByUserUseCase {

	void create(CreateCartItemDTO cartItem);
}
