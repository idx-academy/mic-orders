package com.academy.orders.domain.product.repository;

import com.academy.orders.domain.product.entity.Product;
import java.util.List;
import java.util.UUID;

public interface ProductRepository {
	List<Product> getAllProducts(String language);

	/**
	 * Method sets new quantity of products.
	 *
	 * @param productId id of the product
	 * @param quantity new quantity of the product
	 *
	 * @author Denys Ryhal
	 */
	void setNewProductQuantity(UUID productId, Integer quantity);

	/**
	 * Method checks if product with id already exists.
	 *
	 * @param id id of the product
	 *
	 * @return {@link Boolean}
	 * @author Denys Ryhal
	 */
	boolean existById(UUID id);
}
