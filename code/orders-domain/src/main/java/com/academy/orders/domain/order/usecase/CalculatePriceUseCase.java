package com.academy.orders.domain.order.usecase;

import com.academy.orders.domain.product.entity.Product;
import java.math.BigDecimal;

public interface CalculatePriceUseCase {
	BigDecimal calculatePriceForOrder(Product product, Integer value);
}
