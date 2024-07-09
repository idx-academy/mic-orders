package com.academy.orders.domain.cart.usecase;

import com.academy.orders.domain.cart.dto.CartResponseDto;

public interface GetCartItemsUseCase {
	CartResponseDto getCartItems(Long userId, String lang);
}
