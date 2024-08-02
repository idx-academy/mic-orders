package com.academy.orders.domain.product.usecase;

import com.academy.orders.domain.product.dto.ProductRequestDto;

import java.util.UUID;

public interface UpdateProductUseCase {
	void updateProduct(UUID productId, ProductRequestDto updateProduct);
}
