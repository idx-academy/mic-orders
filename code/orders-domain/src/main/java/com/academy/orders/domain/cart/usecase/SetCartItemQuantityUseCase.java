package com.academy.orders.domain.cart.usecase;

import com.academy.orders.domain.cart.dto.UpdatedCartItemDto;

import java.util.UUID;

public interface SetCartItemQuantityUseCase {
	/**
	 * Sets the quantity of the specified product in the user's shopping cart.
	 *
	 * @param productId
	 *            the UUID of the product whose quantity is to be updated in the
	 *            cart.
	 * @param userId
	 *            the ID of the user whose cart item quantity is to be updated.
	 * @param quantity
	 *            the new quantity to set for the product in the cart.
	 * @return an {@link UpdatedCartItemDto} containing the updated cart item
	 *         details.
	 *
	 * @author Yurii Osovskyi
	 */
	UpdatedCartItemDto setQuantity(UUID productId, Long userId, Integer quantity);
}
