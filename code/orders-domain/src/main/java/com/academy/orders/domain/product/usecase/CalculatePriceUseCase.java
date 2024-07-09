package com.academy.orders.domain.product.usecase;

import com.academy.orders.domain.order.entity.OrderItem;
import com.academy.orders.domain.product.entity.Product;
import java.math.BigDecimal;
import java.util.List;

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

	BigDecimal calculateTotalPrice(List<OrderItem> orderItems);
}