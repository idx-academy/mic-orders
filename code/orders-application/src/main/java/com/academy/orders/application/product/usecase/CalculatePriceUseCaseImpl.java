package com.academy.orders.application.product.usecase;

import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.product.usecase.CalculatePriceUseCase;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalculatePriceUseCaseImpl implements CalculatePriceUseCase {

	@Override
	public BigDecimal calculateCartItemPrice(CartItem cartItem) {
		return cartItem.product().price().multiply(new BigDecimal(cartItem.quantity()));
	}

	@Override
	public BigDecimal calculateCartTotalPrice(List<CartItem> cartItems) {
		return cartItems.stream().map(this::calculateCartItemPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
