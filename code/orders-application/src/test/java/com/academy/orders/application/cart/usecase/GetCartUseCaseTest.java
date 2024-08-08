package com.academy.orders.application.cart.usecase;

import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.cart.repository.CartItemImageRepository;
import com.academy.orders.domain.cart.repository.CartItemRepository;
import com.academy.orders.domain.cart.usecase.CalculatePriceUseCase;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.academy.orders.application.ModelUtils.getCartItem;
import static com.academy.orders.application.ModelUtils.getCartItemDto;
import static com.academy.orders.application.ModelUtils.getCartResponseDto;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCartUseCaseTest {
	@InjectMocks
	private GetCartItemsUseCaseImpl getCartItemsUseCase;
	@Mock
	private CartItemRepository cartItemRepository;
	@Mock
	private CalculatePriceUseCase calculatePriceUseCase;
	@Mock
	private CartItemImageRepository cartItemImageRepository;

	@Test
	void getCartItemsTest() {
		var accountId = 1L;
		var lang = "uk";
		var cartItem = getCartItem();
		var cartItemPrice = BigDecimal.valueOf(100.00);
		var totalPrice = BigDecimal.valueOf(1000.00);
		var cartResponseDto = getCartResponseDto(singletonList(getCartItemDto(cartItem, cartItemPrice)), totalPrice);

		when(cartItemRepository.findCartItemsByAccountIdAndLang(anyLong(), anyString()))
				.thenReturn(singletonList(cartItem));
		when(calculatePriceUseCase.calculateCartItemPrice(any(CartItem.class))).thenReturn(cartItemPrice);
		when(calculatePriceUseCase.calculateCartTotalPrice(anyList())).thenReturn(totalPrice);
		when(cartItemImageRepository.loadImageForProductInCart(cartItem)).thenReturn(cartItem);

		var actualCartItemDto = getCartItemsUseCase.getCartItems(accountId, lang);
		assertEquals(cartResponseDto, actualCartItemDto);

		verify(cartItemRepository).findCartItemsByAccountIdAndLang(anyLong(), anyString());
		verify(calculatePriceUseCase).calculateCartItemPrice(any(CartItem.class));
		verify(calculatePriceUseCase).calculateCartTotalPrice(anyList());
		verify(cartItemImageRepository).loadImageForProductInCart(cartItem);
	}

}
