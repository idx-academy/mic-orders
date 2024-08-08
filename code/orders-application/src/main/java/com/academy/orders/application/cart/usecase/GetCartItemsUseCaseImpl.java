package com.academy.orders.application.cart.usecase;

import com.academy.orders.domain.cart.dto.CartItemDto;
import com.academy.orders.domain.cart.dto.CartResponseDto;
import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.cart.repository.CartItemImageRepository;
import com.academy.orders.domain.cart.repository.CartItemRepository;
import com.academy.orders.domain.cart.usecase.GetCartItemsUseCase;
import com.academy.orders.domain.product.entity.ProductTranslation;
import com.academy.orders.domain.cart.usecase.CalculatePriceUseCase;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCartItemsUseCaseImpl implements GetCartItemsUseCase {
	private final CartItemRepository cartItemRepository;
	private final CalculatePriceUseCase calculatePriceUseCase;
	private final CartItemImageRepository cartItemImageRepository;

	@Override
	public CartResponseDto getCartItems(Long accountId, String lang) {
		var cartItems = getCartItemsByAccountIdAndLang(accountId, lang);
		var cartItemDtos = mapToCartItemsDtos(cartItems);
		var totalPrice = calculateTotalPrice(cartItems);
		return buildCartResponseDTO(cartItemDtos, totalPrice);
	}

	private List<CartItem> getCartItemsByAccountIdAndLang(Long accountId, String lang) {
		return cartItemRepository.findCartItemsByAccountIdAndLang(accountId, lang).stream()
				.map(cartItemImageRepository::loadImageForProductInCart).toList();
	}

	private List<CartItemDto> mapToCartItemsDtos(List<CartItem> cartItems) {
		return cartItems.stream().map(this::toCartItemDto).toList();
	}

	private CartItemDto toCartItemDto(CartItem cartItem) {
		var productItem = cartItem.product();

		return CartItemDto.builder().productId(productItem.id()).image(productItem.image())
				.name(mapName(productItem.productTranslations())).productPrice(productItem.price())
				.quantity(cartItem.quantity()).calculatedPrice(calculatePriceUseCase.calculateCartItemPrice(cartItem))
				.build();
	}

	private String mapName(Set<ProductTranslation> productTranslations) {
		return productTranslations.stream().iterator().next().name();
	}

	private BigDecimal calculateTotalPrice(List<CartItem> cartItems) {
		return calculatePriceUseCase.calculateCartTotalPrice(cartItems);
	}

	private CartResponseDto buildCartResponseDTO(List<CartItemDto> cartItemDtos, BigDecimal totalPrice) {
		return CartResponseDto.builder().items(cartItemDtos).totalPrice(totalPrice).build();
	}

}
