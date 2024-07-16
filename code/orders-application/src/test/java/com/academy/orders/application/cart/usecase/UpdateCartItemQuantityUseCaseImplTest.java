package com.academy.orders.application.cart.usecase;

import com.academy.orders.domain.cart.dto.UpdatedCartItemDto;
import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.cart.entity.CreateCartItemDTO;
import com.academy.orders.domain.cart.exception.CartItemNotFoundException;
import com.academy.orders.domain.cart.exception.ExceedsAvailableException;
import com.academy.orders.domain.cart.repository.CartItemRepository;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.usecase.CalculatePriceUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static com.academy.orders.application.ModelUtils.getCartItem;
import static com.academy.orders.application.ModelUtils.getProductWithQuantity;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCartItemQuantityUseCaseImplTest {
	@InjectMocks
	private UpdateCartItemQuantityUseCaseImpl updateCartItemQuantityUseCase;
	@Mock
	private CartItemRepository cartItemRepository;
	@Mock
	private CalculatePriceUseCase calculatePriceUseCase;

	@Test
	void setQuantitySuccessfulUpdate() {
		UUID productId = UUID.randomUUID();
		Long userId = 1L;
		Integer quantity = 2;

		Product product = getProductWithQuantity(5);

		CartItem cartItem = getCartItem(product, 1);
		List<CartItem> cartItems = List.of(cartItem);

		when(cartItemRepository.existsByProductIdAndUserId(productId, userId)).thenReturn(true);
		when(cartItemRepository.findByProductIdAndUserId(productId, userId)).thenReturn(Optional.of(cartItem));
		when(cartItemRepository.save(any(CreateCartItemDTO.class))).thenReturn(new CartItem(product, quantity));
		when(cartItemRepository.findCartItemsByAccountId(userId)).thenReturn(cartItems);
		when(calculatePriceUseCase.calculateCartItemPrice(any(CartItem.class))).thenReturn(BigDecimal.TEN);
		when(calculatePriceUseCase.calculateCartTotalPrice(anyList())).thenReturn(BigDecimal.valueOf(100));

		UpdatedCartItemDto updatedCartItemDto = updateCartItemQuantityUseCase.setQuantity(productId, userId, quantity);

		Assertions.assertEquals(productId, updatedCartItemDto.productId());
		Assertions.assertEquals(quantity, updatedCartItemDto.quantity());
		Assertions.assertEquals(product.price(), updatedCartItemDto.productPrice());
		Assertions.assertEquals(BigDecimal.TEN, updatedCartItemDto.calculatedPrice());
		Assertions.assertEquals(BigDecimal.valueOf(100), updatedCartItemDto.totalPrice());

		verify(cartItemRepository).existsByProductIdAndUserId(productId, userId);
		verify(cartItemRepository).findByProductIdAndUserId(productId, userId);
		verify(cartItemRepository).save(any(CreateCartItemDTO.class));
		verify(cartItemRepository).findCartItemsByAccountId(userId);
		verify(calculatePriceUseCase).calculateCartItemPrice(any(CartItem.class));
		verify(calculatePriceUseCase).calculateCartTotalPrice(anyList());
	}

	@Test
	void setQuantityCartItemNotFound() {
		UUID productId = UUID.randomUUID();
		Long userId = 1L;
		int quantity = 2;

		when(cartItemRepository.existsByProductIdAndUserId(productId, userId)).thenReturn(false);

		assertThrows(CartItemNotFoundException.class,
				() -> updateCartItemQuantityUseCase.setQuantity(productId, userId, quantity));

		verify(cartItemRepository).existsByProductIdAndUserId(productId, userId);
		verify(cartItemRepository, never()).findByProductIdAndUserId(any(UUID.class), anyLong());
		verify(cartItemRepository, never()).save(any(CreateCartItemDTO.class));
		verify(calculatePriceUseCase, never()).calculateCartItemPrice(any(CartItem.class));
		verify(calculatePriceUseCase, never()).calculateCartTotalPrice(anyList());
	}

	@Test
	void setQuantityExceedsAvailableException() {
		UUID productId = UUID.randomUUID();
		Long userId = 1L;
		Integer quantity = 6;

		Product product = getProductWithQuantity(5);

		CartItem cartItem = getCartItem(product, 1);

		when(cartItemRepository.existsByProductIdAndUserId(productId, userId)).thenReturn(true);
		when(cartItemRepository.findByProductIdAndUserId(productId, userId)).thenReturn(Optional.of(cartItem));
		when(cartItemRepository.save(any(CreateCartItemDTO.class))).thenReturn(new CartItem(product, quantity));

		ExceedsAvailableException exception = assertThrows(ExceedsAvailableException.class,
				() -> updateCartItemQuantityUseCase.setQuantity(productId, userId, quantity));

		Assertions.assertEquals(String.format("Product with id: %s exceeded available quantity", productId),
				exception.getMessage());

		verify(cartItemRepository).existsByProductIdAndUserId(productId, userId);
		verify(cartItemRepository).findByProductIdAndUserId(productId, userId);
		verify(cartItemRepository).save(any(CreateCartItemDTO.class));
		verify(calculatePriceUseCase, never()).calculateCartItemPrice(any(CartItem.class));
		verify(calculatePriceUseCase, never()).calculateCartTotalPrice(anyList());
	}

	@Test
	void setQuantityEqualProductQuantity() {
		UUID productId = UUID.randomUUID();
		Long userId = 1L;
		Integer quantity = 5;

		Product product = getProductWithQuantity(5);

		CartItem cartItem = getCartItem(product, 1);
		List<CartItem> cartItems = List.of(cartItem);

		when(cartItemRepository.existsByProductIdAndUserId(productId, userId)).thenReturn(true);
		when(cartItemRepository.findByProductIdAndUserId(productId, userId)).thenReturn(Optional.of(cartItem));
		when(cartItemRepository.save(any(CreateCartItemDTO.class))).thenReturn(new CartItem(product, quantity));
		when(cartItemRepository.findCartItemsByAccountId(userId)).thenReturn(cartItems);
		when(calculatePriceUseCase.calculateCartItemPrice(any(CartItem.class))).thenReturn(BigDecimal.TEN);
		when(calculatePriceUseCase.calculateCartTotalPrice(anyList())).thenReturn(BigDecimal.valueOf(100));

		UpdatedCartItemDto updatedCartItemDto = updateCartItemQuantityUseCase.setQuantity(productId, userId, quantity);

		Assertions.assertEquals(productId, updatedCartItemDto.productId());
		Assertions.assertEquals(quantity, updatedCartItemDto.quantity());
		Assertions.assertEquals(product.price(), updatedCartItemDto.productPrice());
		Assertions.assertEquals(BigDecimal.TEN, updatedCartItemDto.calculatedPrice());
		Assertions.assertEquals(BigDecimal.valueOf(100), updatedCartItemDto.totalPrice());

		verify(cartItemRepository).existsByProductIdAndUserId(productId, userId);
		verify(cartItemRepository).findByProductIdAndUserId(productId, userId);
		verify(cartItemRepository).save(any(CreateCartItemDTO.class));
		verify(cartItemRepository).findCartItemsByAccountId(userId);
		verify(calculatePriceUseCase).calculateCartItemPrice(any(CartItem.class));
		verify(calculatePriceUseCase).calculateCartTotalPrice(anyList());
	}

	@Test
	void setQuantityUpdateQuantityOfCartItemReturnsNull() {
		UUID productId = UUID.randomUUID();
		Long userId = 1L;
		Integer quantity = 2;

		when(cartItemRepository.existsByProductIdAndUserId(productId, userId)).thenReturn(true);
		when(cartItemRepository.findByProductIdAndUserId(productId, userId)).thenReturn(Optional.empty());

		assertThrows(CartItemNotFoundException.class,
				() -> updateCartItemQuantityUseCase.setQuantity(productId, userId, quantity));

		verify(cartItemRepository).existsByProductIdAndUserId(productId, userId);
		verify(cartItemRepository).findByProductIdAndUserId(productId, userId);
		verify(cartItemRepository, never()).save(any(CreateCartItemDTO.class));
		verify(calculatePriceUseCase, never()).calculateCartItemPrice(any(CartItem.class));
		verify(calculatePriceUseCase, never()).calculateCartTotalPrice(anyList());
	}
}
