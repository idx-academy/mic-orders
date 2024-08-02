package com.academy.orders.boot.infrastructure.cart.repository;

import com.academy.orders.boot.infrastructure.common.repository.AbstractRepository;
import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.cart.entity.CreateCartItemDTO;
import com.academy.orders.domain.cart.repository.CartItemRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.of;

class CartItemRepositoryIT extends AbstractRepository {
	private static final UUID productId = UUID.fromString("f4831fef-35a8-4766-b50e-dcb25d7b2e7b");
	private static final Long userId = 2L;

	@Autowired
	private CartItemRepository cartItemRepository;

	@ParameterizedTest
	@MethodSource("productIdAndUserIdMethodSourceProvider")
	void existsByProductIdAndUserIdTest(UUID productId, Long userId, Boolean expected) {
		final var actual = cartItemRepository.existsByProductIdAndUserId(productId, userId);

		assertEquals(expected, actual);
	}

	@Test
	void saveTest() {
		var quantity = 10;
		var createCartItemDTO = new CreateCartItemDTO(productId, userId, quantity);

		var savedCartItem = cartItemRepository.save(createCartItemDTO);

		assertNotNull(savedCartItem);
		assertNotNull(savedCartItem.product());
		assertEquals(productId, savedCartItem.product().id());
		assertEquals(quantity, savedCartItem.quantity());
	}

	@Test
	void findCartItemsByAccountIdTest() {
		var expectedSize = 3;
		final var cartItems = cartItemRepository.findCartItemsByAccountId(userId);

		assertNotNull(cartItems);
		assertEquals(expectedSize, cartItems.size());
		assertSchemaIsNotNull(cartItems);
	}

	@Test
	void deleteCartItemsByAccountIdTest() {
		cartItemRepository.deleteCartItemsByAccountId(userId);
		final var cartItems = cartItemRepository.findCartItemsByAccountId(userId);

		assertTrue(cartItems.isEmpty());
	}

	@Test
	void findByProductIdAndUserIdTest() {
		cartItemRepository.findByProductIdAndUserId(productId, userId);

		var cartItem = cartItemRepository.findByProductIdAndUserId(productId, userId);
		assertTrue(cartItem.isPresent());
		assertSchemaIsNotNull(cartItem.get());
	}

	@ParameterizedTest
	@MethodSource("findByProductIdAndUserIdMethodSourceProvider")
	void findByProductIdAndUserIdIfNotFoundTest(UUID productId, Long userId) {
		var cartItem = cartItemRepository.findByProductIdAndUserId(productId, userId);
		assertTrue(cartItem.isEmpty());
	}

	@Test
	void deleteCartItemByAccountAndProductIdsTest() {
		cartItemRepository.deleteCartItemByAccountAndProductIds(userId, productId);
		var cartItem = cartItemRepository.findByProductIdAndUserId(productId, userId);

		assertTrue(cartItem.isEmpty());
	}

	@Test
	void findCartItemsByAccountIdAndLangTest() {
		var lang = "en";
		var expectedSize = 3;
		var cartItems = cartItemRepository.findCartItemsByAccountIdAndLang(userId, lang);
		assertEquals(expectedSize, cartItems.size());
		assertSchemaIsNotNull(cartItems);
		assertEquals(1, cartItems.get(0).product().productTranslations().size());
	}

	static Stream<Arguments> productIdAndUserIdMethodSourceProvider() {
		return Stream.of(of(productId, userId, true), of(UUID.randomUUID(), userId, false),
				of(productId, Long.MAX_VALUE, false));
	}

	static Stream<Arguments> findByProductIdAndUserIdMethodSourceProvider() {
		return Stream.of(of(UUID.randomUUID(), userId), of(productId, Long.MAX_VALUE));
	}

	private void assertSchemaIsNotNull(List<CartItem> cartItems) {
		cartItems.forEach(cartItem -> {
			assertNotNull(cartItem.product());
			assertNotNull(cartItem.quantity());
		});
	}

	private void assertSchemaIsNotNull(CartItem cartItem) {
		assertNotNull(cartItem.product());
		assertNotNull(cartItem.quantity());
	}
}
