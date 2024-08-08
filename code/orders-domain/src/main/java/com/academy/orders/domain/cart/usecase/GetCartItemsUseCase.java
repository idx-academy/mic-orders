package com.academy.orders.domain.cart.usecase;

import com.academy.orders.domain.cart.dto.CartResponseDto;

/**
 * Use case interface for retrieving the items in a user's shopping cart.
 */
public interface GetCartItemsUseCase {

	/**
	 * Retrieves the items in the user's shopping cart.
	 *
	 * @param userId
	 *            the ID of the user whose cart items are to be retrieved.
	 * @param lang
	 *            the language preference for the response.
	 * @return a {@link CartResponseDto} containing the details of the cart items.
	 *
	 * @author Denys Ryhal
	 */
	CartResponseDto getCartItems(Long userId, String lang);
}
