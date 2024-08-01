package com.academy.orders.domain.product.usecase;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.product.entity.Product;
import java.util.List;

public interface GetAllProductsUseCase {
	Page<Product> getAllProducts(String language, Pageable pageable, List<String> tags);
}
