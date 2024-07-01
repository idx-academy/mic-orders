package com.academy.orders.application.cart.usecase;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.cart.entity.CreateCartItemDTO;
import com.academy.orders.domain.cart.repository.CartItemRepository;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.exception.ProductNotFoundException;
import com.academy.orders.domain.product.repository.ProductRepository;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.academy.orders.application.ModelUtils.getAccount;
import static com.academy.orders.application.ModelUtils.getProduct;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCartItemByUserUseCaseUseCaseTest {
	@InjectMocks
	private CreateCartItemByUserUseCaseUseCaseImpl createCartItemByUserUseCase;
	@Mock
	private CartItemRepository cartItemRepository;
	@Mock
	private ProductRepository productRepository;

	private CreateCartItemDTO cartItemDTO;
	private Product product;
	private Account account;

	@BeforeEach
	void setUp() {
		account = getAccount();
		product = getProduct();

		cartItemDTO = CreateCartItemDTO.builder().productId(product.id()).userId(account.id()).quantity(1).build();
	}

	@Test
	void testCreateCartItemByUserIfProductIsNotAddedToTheCart() {
		var cartItem = CartItem.builder().product(product).account(account).quantity(1).build();

		when(productRepository.existById(product.id())).thenReturn(true);
		when(cartItemRepository.existsByProductIdAndUserId(cartItemDTO.productId(), cartItemDTO.userId()))
				.thenReturn(false);
		when(cartItemRepository.save(cartItemDTO)).thenReturn(cartItem);

		assertDoesNotThrow(() -> createCartItemByUserUseCase.create(cartItemDTO));

		verify(productRepository).existById(any(UUID.class));
		verify(cartItemRepository).existsByProductIdAndUserId(any(UUID.class), anyLong());
		verify(cartItemRepository).save(cartItemDTO);
	}

	@Test
	void testCreateCartItemByUserIfProductIsAddedToTheCart() {
		when(productRepository.existById(product.id())).thenReturn(true);
		when(cartItemRepository.existsByProductIdAndUserId(cartItemDTO.productId(), cartItemDTO.userId()))
				.thenReturn(true);
		doNothing().when(cartItemRepository).incrementQuantity(product.id(), account.id());

		assertDoesNotThrow(() -> createCartItemByUserUseCase.create(cartItemDTO));

		verify(productRepository).existById(any(UUID.class));
		verify(cartItemRepository).existsByProductIdAndUserId(any(UUID.class), anyLong());
		verify(cartItemRepository).incrementQuantity(any(UUID.class), anyLong());
		verify(cartItemRepository, never()).save(cartItemDTO);

	}

	@Test
	void testCreateCartItemByUserIfProductDoesNotExists() {
		when(productRepository.existById(product.id())).thenReturn(false);

		assertThrowsExactly(ProductNotFoundException.class, () -> createCartItemByUserUseCase.create(cartItemDTO));
		verify(productRepository).existById(any(UUID.class));
	}
}
