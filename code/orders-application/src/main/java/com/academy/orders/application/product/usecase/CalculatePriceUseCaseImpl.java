package com.academy.orders.application.product.usecase;

import com.academy.orders.domain.order.entity.OrderItem;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.usecase.CalculatePriceUseCase;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalculatePriceUseCaseImpl implements CalculatePriceUseCase {
	@Override
	public BigDecimal calculateTotalPrice(Product product, Integer quantity) {
		return product.price().multiply(new BigDecimal(quantity));
	}

	@Override
	public BigDecimal calculateTotalPrice(List<OrderItem> orderItems) {
		return orderItems.stream().map(OrderItem::price).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}