package com.academy.orders.application.product.usecase;

import com.academy.orders.application.ModelUtils;
import com.academy.orders.application.cart.usecase.CalculatePriceUseCaseImpl;
import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.product.entity.Product;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CalculatePriceUseCaseTest {
	@InjectMocks
	private CalculatePriceUseCaseImpl calculatePriceUseCase;

	@Test
	void calculatePriceTest() {
		var cartItem = ModelUtils.getCartItem();
		var expectedResult = cartItem.product().getPrice().multiply(BigDecimal.valueOf(cartItem.quantity()));

		var actualPrice = calculatePriceUseCase.calculateCartItemPrice(cartItem);
		assertEquals(expectedResult, actualPrice);
	}

	@Test
	void calculateCartTotalPriceTest() {
		var actualPrice = calculatePriceUseCase.calculateCartTotalPrice(cartItems());

		assertEquals(BigDecimal.valueOf(25_200), actualPrice);
	}

	private static List<CartItem> cartItems() {
		return List.of(
				CartItem.builder().product(Product.builder().price(BigDecimal.valueOf(1500)).build()).quantity(10)
						.build(),
				CartItem.builder().product(Product.builder().price(BigDecimal.valueOf(2000)).build()).quantity(5)
						.build(),
				CartItem.builder().product(Product.builder().price(BigDecimal.valueOf(100)).build()).quantity(2)
						.build());
	}
}
