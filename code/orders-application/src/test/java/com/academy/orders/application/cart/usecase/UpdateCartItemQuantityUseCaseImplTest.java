package com.academy.orders.application.cart.usecase;

import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.cart.entity.CreateCartItemDTO;
import com.academy.orders.domain.cart.exception.CartItemNotFoundException;
import com.academy.orders.domain.cart.repository.CartItemRepository;
import com.academy.orders.domain.product.usecase.CalculatePriceUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;
import static org.junit.Assert.assertThrows;
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
}
