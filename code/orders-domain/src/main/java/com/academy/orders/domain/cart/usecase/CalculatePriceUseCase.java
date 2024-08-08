package com.academy.orders.domain.cart.usecase;

import com.academy.orders.domain.cart.entity.CartItem;
import java.math.BigDecimal;
import java.util.List;

/**
 * Use case interface for calculating price for cart item.
 */
public interface CalculatePriceUseCase {

	/**
	 * Method calculates price for cartItem.
	 *
	 * @return {@link BigDecimal} calculated price
	 *
	 * @author Denys Ryhal
	 */
	BigDecimal calculateCartItemPrice(CartItem cartItem);

	/**
	 * Method calculates total price of cart.
	 *
	 * @return {@link BigDecimal} total price of cart
	 *
	 * @author Denys Ryhal
	 */
	BigDecimal calculateCartTotalPrice(List<CartItem> cartItems);
}
