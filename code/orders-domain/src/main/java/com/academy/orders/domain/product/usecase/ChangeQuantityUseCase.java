package com.academy.orders.domain.product.usecase;

import com.academy.orders.domain.product.entity.Product;

/**
 * Use case interface for changing quantity of product.
 */
public interface ChangeQuantityUseCase {
	/**
	 * Method changes the quantity of the product.
	 *
	 * @param product
	 *            the {@link Product} whose quantity should be changed
	 * @param orderedQuantity
	 *            the {@link Integer} quantity of the product chosen by the user to
	 *            order
	 *
	 * @author Denys Ryhal
	 */
	void changeQuantityOfProduct(Product product, Integer orderedQuantity);
}
