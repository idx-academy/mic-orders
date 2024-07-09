package com.academy.orders.domain.product.usecase;

import com.academy.orders.domain.product.entity.Product;
import java.math.BigDecimal;

public interface CalculatePriceUseCase {
	/**
	 * Method calculates price of product.
	 *
	 * @param product
	 *            product which price should be calculated
	 * @param quantity
	 *            quantity of the products
	 *
	 * @return {@link BigDecimal} calculated price
	 * @author Denys Ryhal
	 */
	BigDecimal calculateTotalPrice(Product product, Integer quantity);
}