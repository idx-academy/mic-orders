package com.academy.orders.domain.product.usecase;

import com.academy.orders.domain.product.entity.Product;

import java.util.UUID;

public interface GetProductDetailsByIdUseCase {
	Product getProductDetailsById(UUID productId, String lang);
}
