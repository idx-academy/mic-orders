package com.academy.orders.domain.cart.repository;

import com.academy.orders.domain.cart.entity.CartItem;

/**
 * Repository interface for managing and loading images for products in a
 * shopping cart.
 */
public interface CartItemImageRepository {

	/**
	 * Loads the image for the product associated with the given cart item.
	 *
	 * @param cartItem
	 *            the {@link CartItem} for which the product image needs to be
	 *            loaded.
	 * @return the updated {@link CartItem} with the product image loaded.
	 *
	 * @author Denys Ryhal
	 */
	CartItem loadImageForProductInCart(CartItem cartItem);
}
