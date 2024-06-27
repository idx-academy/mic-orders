package com.academy.orders.domain.product.repository;

import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.valueobject.PageRequest;
import com.academy.orders.domain.product.valueobject.PageResponse;

public interface ProductRepository {
	PageResponse<Product> getAllProducts(String language, PageRequest pageRequest);
}
