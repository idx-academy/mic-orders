package com.academy.orders.domain.order.usecase;

import com.academy.orders.domain.product.entity.Product;

public interface ChangeQuantityUseCase {
	void changeQuantityOfProduct(Product product, Integer orderedQuantity);
}
