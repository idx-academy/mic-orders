package com.academy.orders.domain.product.usecase;

import com.academy.orders.domain.product.entity.Product;

import com.academy.orders.domain.product.exception.ProductNotFoundException;
import java.util.UUID;

/**
 * Use case interface for getting product details.
 */
public interface GetProductDetailsByIdUseCase {
	/**
	 * Retrieves detailed information about a product by its unique identifier, with
	 * localization based on the provided language.
	 *
	 * @param productId
	 *            the UUID of the product to be retrieved.
	 * @param lang
	 *            the language code for localizing the product details.
	 *
	 * @return the {@link Product} with localized details associated with the
	 *         specified ID.
	 *
	 * @throws ProductNotFoundException
	 *             if no product with the specified ID is found.
	 *
	 * @author Anton Bodnar
	 */
	Product getProductDetailsById(UUID productId, String lang);
}
