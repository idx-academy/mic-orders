package com.academy.orders.domain.order.usecase;

import com.academy.orders.domain.product.entity.Product;
import java.math.BigDecimal;

public interface CalculatePriceUseCase {

	/**
	 * Method calculates price of product.
	 *
	 * @param product
	 *            product which price should be calculated
	 * @param quantity
	 *            quantity of the products to order
	 *
	 * @return {@link BigDecimal} calculated price
	 * @author Denys Ryhal
	 */
	BigDecimal calculatePriceForOrder(Product product, Integer quantity);
}
