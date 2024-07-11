package com.academy.orders.domain.product.usecase;

import com.academy.orders.domain.cart.entity.CartItem;
import java.math.BigDecimal;
import java.util.List;

public interface CalculatePriceUseCase {

	/**
	 * Method calculates price for cartItem.
	 *
	 * @return {@link BigDecimal} calculated price
	 * @author Denys Ryhal
	 */
	BigDecimal calculateCartItemPrice(CartItem cartItem);

	/**
	 * Method calculates total price of cart.
	 *
	 * @return {@link BigDecimal} total price of cart
	 * @author Denys Ryhal
	 */
	BigDecimal calculateCartTotalPrice(List<CartItem> cartItems);
}
