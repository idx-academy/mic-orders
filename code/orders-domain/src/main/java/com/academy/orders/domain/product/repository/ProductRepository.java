package com.academy.orders.domain.product.repository;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.product.entity.Product;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
	Page<Product> getAllProducts(String language, Pageable pageable);

	/**
	 * Method sets new quantity of products.
	 *
	 * @param productId
	 *            id of the product
	 * @param quantity
	 *            new quantity of the product
	 *
	 * @author Denys Ryhal
	 */
	void setNewProductQuantity(UUID productId, Integer quantity);

	/**
	 * Method checks if product with id already exists.
	 *
	 * @param id
	 *            id of the product
	 *
	 * @return {@link Boolean}
	 * @author Denys Ryhal
	 */
	boolean existById(UUID id);

	/**
	 * Method to get the quantity of a product by its ID.
	 *
	 * @param productId
	 *            UUID of the product.
	 * @return Optional<Integer> containing the quantity if found.
	 */
	Optional<Integer> findQuantityById(UUID productId);
}
