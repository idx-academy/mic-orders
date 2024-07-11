package com.academy.orders.domain.cart.usecase;

import com.academy.orders.domain.cart.dto.QuantityCheckResponseDto;
import java.util.UUID;

public interface CheckProductQuantityUseCase {
	QuantityCheckResponseDto checkQuantity(Long userId, UUID productId, int quantity);
}
