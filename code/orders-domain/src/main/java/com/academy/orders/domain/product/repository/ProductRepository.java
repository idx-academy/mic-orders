package com.academy.orders.domain.product.repository;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.product.entity.Product;
import java.util.UUID;

public interface ProductRepository {
	Page<Product> getAllProducts(String language, Pageable pageable);
	boolean existById(UUID id);
}
