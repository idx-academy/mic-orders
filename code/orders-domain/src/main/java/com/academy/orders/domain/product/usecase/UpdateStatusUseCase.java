package com.academy.orders.domain.product.usecase;

import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import java.util.UUID;

public interface UpdateStatusUseCase {
	void updateStatus(UUID productId, ProductStatus status);
}
