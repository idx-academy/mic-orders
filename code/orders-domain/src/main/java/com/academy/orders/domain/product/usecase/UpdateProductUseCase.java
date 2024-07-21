package com.academy.orders.domain.product.usecase;

import com.academy.orders.domain.product.entity.UpdateProduct;

import java.util.UUID;

public interface UpdateProductUseCase {
	void updateProduct(UUID productId, String lang, UpdateProduct updateProduct);
}
