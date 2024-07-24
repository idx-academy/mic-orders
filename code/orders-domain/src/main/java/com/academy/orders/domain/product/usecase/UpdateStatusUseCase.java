package com.academy.orders.domain.product.usecase;

import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import java.util.UUID;

public interface UpdateStatusUseCase {
	/**
	 * Updates the status of a product identified by its UUID.
	 *
	 * @param productId
	 *            the UUID of the product whose status is to be updated.
	 * @param status
	 *            the new status to be set for the product.
	 *
	 * @author Denys Liubchenko
	 */
	void updateStatus(UUID productId, ProductStatus status);
}
