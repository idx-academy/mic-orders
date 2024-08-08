package com.academy.orders.domain.cart.usecase;

import com.academy.orders.domain.cart.entity.CreateCartItemDTO;

public interface CreateCartItemByUserUseCase {

	/**
	 * Adds product to the cart
	 *
	 * @param cartItem
	 *            with type {@link CreateCartItemDTO}
	 * @author Ryhal Denys, Oleksii Siianchuk
	 */
	void create(CreateCartItemDTO cartItem);
}
