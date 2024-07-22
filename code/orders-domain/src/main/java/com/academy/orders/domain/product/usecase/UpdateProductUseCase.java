package com.academy.orders.domain.product.usecase;

import com.academy.orders.domain.product.dto.UpdateProductDto;

import java.util.UUID;

public interface UpdateProductUseCase {
	void updateProduct(UUID productId, String lang, UpdateProductDto updateProduct);
}
