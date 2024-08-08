package com.academy.orders.boot.infrastructure.cart.repository;

import com.academy.orders.boot.Application;
import com.academy.orders.domain.cart.repository.CartItemImageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.academy.orders.ModelUtils.getCartItem;
import static com.academy.orders.ModelUtils.getProductWithImageLink;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class)
@ActiveProfiles("it")
class CartImageRepositoryIT {
	@Autowired
	private CartItemImageRepository cartItemImageRepository;

	@Test
	void loadOrderItemForProductInOrderItemTest() {
		var cartItem = getCartItem();
		var productWithImageLink = getProductWithImageLink();

		var actual = cartItemImageRepository.loadImageForProductInCart(cartItem);

		assertEquals(cartItem.quantity(), actual.quantity());
		assertEquals(productWithImageLink, actual.product());
	}
}