package com.academy.orders.domain.product.usecase;

import com.academy.orders.domain.product.dto.UpdateProductDiscountRequestDto;

public interface UpdateProductDiscountUseCase {
	void updateDiscount(final UpdateProductDiscountRequestDto updateProductDiscountRequestDto);
}
