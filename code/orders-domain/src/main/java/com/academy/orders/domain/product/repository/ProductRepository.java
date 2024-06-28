package com.academy.orders.domain.product.repository;

import com.academy.orders.domain.product.entity.Product;
import java.util.List;
import java.util.UUID;

public interface ProductRepository {
	List<Product> getAllProducts(String language);
	List<Product> findProductsWithPricesAndQuantities(UUID... productsIds);
	void setNewProductQuantity(UUID productId, Integer quantity);
}
