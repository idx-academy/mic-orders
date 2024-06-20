package com.academy.orders.domain.product.usecase;

import com.academy.orders.domain.product.entity.Product;
import java.util.List;

public interface GetAllProductsUseCase {
	List<Product> getAllProducts();
}
