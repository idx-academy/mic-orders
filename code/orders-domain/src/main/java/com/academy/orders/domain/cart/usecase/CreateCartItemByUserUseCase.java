package com.academy.orders.domain.cart.usecase;

import com.academy.orders.domain.cart.entity.CreateCartItemDTO;

/**
 * Use case interface for adding cart item to user's cart.
 */
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
