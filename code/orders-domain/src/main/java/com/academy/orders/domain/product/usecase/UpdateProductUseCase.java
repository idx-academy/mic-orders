package com.academy.orders.domain.product.usecase;

import com.academy.orders.domain.product.dto.ProductRequestDto;

import com.academy.orders.domain.product.exception.ProductNotFoundException;
import java.util.UUID;

public interface UpdateProductUseCase {
	/**
	 * Updates the details of an existing product based on the provided product ID
	 * and update information.
	 *
	 * @param productId
	 *            the UUID of the product to be updated.
	 * @param updateProduct
	 *            the {@link ProductRequestDto} containing updated information for
	 *            the product.
	 * @throws ProductNotFoundException
	 *             if no product with the specified ID is found.
	 *
	 * @author Anton Bodnar
	 */
	void updateProduct(UUID productId, ProductRequestDto updateProduct);
}
