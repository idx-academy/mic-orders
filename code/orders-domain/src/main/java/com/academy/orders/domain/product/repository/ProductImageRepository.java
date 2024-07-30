package com.academy.orders.domain.product.repository;

import com.academy.orders.domain.product.entity.Product;

public interface ProductImageRepository {
	Product loadImageForProduct(Product product);
}
