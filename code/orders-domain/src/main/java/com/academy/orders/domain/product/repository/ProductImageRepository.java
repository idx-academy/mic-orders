package com.academy.orders.domain.product.repository;

import com.academy.orders.domain.product.entity.Product;

public interface ProductImageRepository {
	/**
	 * Loads an image for the given product.
	 *
	 * @param product
	 *            the product for which the image needs to be loaded
	 *
	 * @return the product with the loaded image
	 *
	 * @author Denys Ryhal
	 */
	Product loadImageForProduct(Product product);
}
