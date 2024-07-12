package com.academy.orders.application.cart.usecase;

import com.academy.orders.domain.cart.dto.UpdatedCartItemDto;
import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.cart.entity.CreateCartItemDTO;
import com.academy.orders.domain.cart.exception.CartItemNotFoundException;
import com.academy.orders.domain.cart.exception.ExceedsAvailableException;
import com.academy.orders.domain.cart.repository.CartItemRepository;
import com.academy.orders.domain.cart.usecase.SetCartItemQuantityUseCase;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.exception.ProductNotFoundException;
import com.academy.orders.domain.product.repository.ProductRepository;
import com.academy.orders.domain.product.usecase.CalculatePriceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateCartItemQuantityUseCaseImpl implements SetCartItemQuantityUseCase {
	private final CartItemRepository cartItemRepository;
	private final ProductRepository productRepository;
	private final CalculatePriceUseCase calculatePriceUseCase;

	@Override
	@Transactional
	public UpdatedCartItemDto setQuantity(UUID productId, Long userId, Integer quantity) {
		CartItem cartItem = cartItemRepository.findByProductIdAndUserId(productId, userId)
				.orElseThrow(() -> new CartItemNotFoundException(productId));

		if (!productRepository.existById(productId)) {
			throw new ProductNotFoundException(productId);
		}

		updateQuantityOfCartItem(quantity, productId, userId);

		Product product = (Product) productRepository.findById(productId).orElseThrow();
		if (quantity > product.quantity()) {
			throw new ExceedsAvailableException(productId, quantity);
		}

		// Update the quantity of the cart item
		updateQuantityOfCartItem(quantity, productId, userId);

		// Fetch all cart items for the user
		List<CartItem> cartItems = getAll(userId);

		// Calculate prices
		var cartItemsPrice = calculatePriceUseCase.calculateCartItemPrice(cartItem);
		var totalPrice = calculatePriceUseCase.calculateCartTotalPrice(cartItems);
		return new UpdatedCartItemDto(productId, quantity, cartItemsPrice, totalPrice);
	}

	private List<CartItem> getAll(Long userId) {
		return cartItemRepository.findCartItemsByAccountId(userId);
	}

	private void updateQuantityOfCartItem(Integer quantity, UUID productId, Long userId) {
		CartItem cartItem = cartItemRepository.findByProductIdAndUserId(productId, userId)
				.orElseThrow(() -> new CartItemNotFoundException(productId));
		// Create a new cart item with the updated quantity
		CartItem updatedCartItem = new CartItem(cartItem.product(), quantity);

		// Save the new cart item
		cartItemRepository
				.save(new CreateCartItemDTO(updatedCartItem.product().id(), userId, updatedCartItem.quantity()));
	}
}
