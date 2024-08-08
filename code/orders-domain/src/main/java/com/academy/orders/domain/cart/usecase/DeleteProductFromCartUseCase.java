package com.academy.orders.domain.cart.usecase;

import com.academy.orders.domain.cart.exception.CartItemNotFoundException;
import java.util.UUID;

/**
 * Use case interface for deleting a product from a user's shopping cart.
 */
public interface DeleteProductFromCartUseCase {

	/**
	 * Deletes the specified product from the user's shopping cart.
	 *
	 * @param userId
	 *            the ID of the user whose cart the product will be removed from.
	 * @param productId
	 *            the UUID of the product to be removed from the cart.
	 *
	 * @throws CartItemNotFoundException
	 *             if account is not found.
	 *
	 * @author Denys Ryhal
	 */
	void deleteProductFromCart(Long userId, UUID productId);
}