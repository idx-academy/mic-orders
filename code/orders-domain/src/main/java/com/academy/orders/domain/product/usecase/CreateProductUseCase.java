package com.academy.orders.domain.product.usecase;

import com.academy.orders.domain.product.dto.ProductRequestDto;
import com.academy.orders.domain.product.entity.Product;

/**
 * Use case interface for adding new product.
 */
public interface CreateProductUseCase {
	/**
	 * Creates a new product based on the provided product request details.
	 *
	 * @param product
	 *            the {@link ProductRequestDto} containing information about the new
	 *            product to be created.
	 *
	 * @return the {@link Product} that was created.
	 *
	 */
	Product createProduct(ProductRequestDto product);
}
