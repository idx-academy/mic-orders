package com.academy.orders.application.order.usecase;

import com.academy.orders.domain.order.entity.OrderItem;
import com.academy.orders.domain.order.usecase.CalculateOrderTotalPriceUseCase;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalculateOrderTotalPriceUseCaseImpl implements CalculateOrderTotalPriceUseCase {
	@Override
	public BigDecimal calculateTotalPrice(List<OrderItem> orderItems) {
		return orderItems.stream().map(OrderItem::price).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
