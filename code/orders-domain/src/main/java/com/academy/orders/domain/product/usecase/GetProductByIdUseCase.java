package com.academy.orders.domain.product.usecase;

import com.academy.orders.domain.product.entity.Product;

import com.academy.orders.domain.product.exception.ProductNotFoundException;
import java.util.UUID;

public interface GetProductByIdUseCase {
	/**
	 * Retrieves a product by its unique identifier.
	 *
	 * @param productId
	 *            the UUID of the product to be retrieved.
	 * @return the {@link Product} associated with the specified ID.
	 * @throws ProductNotFoundException
	 *             if no product with the specified ID is found.
	 *
	 * @author Anton Bodnar
	 */
	Product getProductById(UUID productId);
}
