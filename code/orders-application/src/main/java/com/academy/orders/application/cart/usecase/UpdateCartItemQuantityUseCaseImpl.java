package com.academy.orders.application.cart.usecase;

import com.academy.orders.domain.cart.dto.UpdatedCartItemDto;
import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.cart.entity.CreateCartItemDTO;
import com.academy.orders.domain.cart.exception.CartItemNotFoundException;
import com.academy.orders.domain.cart.exception.ExceedsAvailableException;
import com.academy.orders.domain.cart.repository.CartItemRepository;
import com.academy.orders.domain.cart.usecase.SetCartItemQuantityUseCase;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.usecase.CalculatePriceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateCartItemQuantityUseCaseImpl implements SetCartItemQuantityUseCase {
	private final CartItemRepository cartItemRepository;
	private final CalculatePriceUseCase calculatePriceUseCase;

	@Override
	@Transactional
	public UpdatedCartItemDto setQuantity(UUID productId, Long userId, Integer quantity) {

		checkCartItemExists(productId, userId);

		var updatedCartItem = updateQuantityOfCartItem(quantity, productId, userId);
		var getAll = cartItemRepository.findCartItemsByAccountId(userId);

		Product product = updatedCartItem.product();

		if (quantity > product.quantity()) {
			throw new ExceedsAvailableException(productId, quantity);
		}

		var cartItemPrice = calculatePriceUseCase.calculateCartItemPrice(updatedCartItem);
		var totalPrice = calculatePriceUseCase.calculateCartTotalPrice(getAll);

		return new UpdatedCartItemDto(productId, quantity, product.price(), cartItemPrice, totalPrice);
	}

	private void checkCartItemExists(UUID productId, Long userId) {
		if (!Boolean.TRUE.equals(cartItemRepository.existsByProductIdAndUserId(productId, userId))) {
			throw new CartItemNotFoundException(productId);
		}
	}

	private CartItem updateQuantityOfCartItem(Integer quantity, UUID productId, Long userId) {
		CartItem cartItem = cartItemRepository.findByProductIdAndUserId(productId, userId)
				.orElseThrow(() -> new CartItemNotFoundException(productId));
		CartItem updatedCartItem = new CartItem(cartItem.product(), quantity);

		return cartItemRepository
				.save(new CreateCartItemDTO(updatedCartItem.product().id(), userId, updatedCartItem.quantity()));
	}
}
