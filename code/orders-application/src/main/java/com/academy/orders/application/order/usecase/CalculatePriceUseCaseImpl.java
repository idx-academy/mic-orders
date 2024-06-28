package com.academy.orders.application.order.usecase;

import com.academy.orders.domain.order.usecase.CalculatePriceUseCase;
import com.academy.orders.domain.product.entity.Product;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalculatePriceUseCaseImpl implements CalculatePriceUseCase {

	@Override
	public BigDecimal calculatePriceForOrder(Product product, Integer value) {
		return product.price().multiply(new BigDecimal(value));
	}
}
