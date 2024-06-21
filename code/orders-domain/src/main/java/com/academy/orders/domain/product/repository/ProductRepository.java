package com.academy.orders.domain.product.repository;

import com.academy.orders.domain.product.entity.Product;
import java.util.List;

public interface ProductRepository {
	List<Product> getAllProducts(String language);
}
