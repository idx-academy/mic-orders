package com.academy.orders.domain.product.usecase;

import com.academy.orders.domain.product.dto.CreateProductRequestDto;
import com.academy.orders.domain.product.entity.Product;

public interface CreateProductUseCase {
	Product createProduct(CreateProductRequestDto product);
}
