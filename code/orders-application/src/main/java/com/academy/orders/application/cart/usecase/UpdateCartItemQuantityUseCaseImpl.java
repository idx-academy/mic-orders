package com.academy.orders.application.cart.usecase;

import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.cart.exception.CartItemNotFoundException;
import com.academy.orders.domain.cart.exception.ExceedsAvailableException;
import com.academy.orders.domain.cart.repository.CartItemRepository;
import com.academy.orders.domain.cart.usecase.SetCartItemQuantityUseCase;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.exception.ProductNotFoundException;
import com.academy.orders.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateCartItemQuantityUseCaseImpl implements SetCartItemQuantityUseCase {
	private final CartItemRepository cartItemRepository;
	private final ProductRepository productRepository;

	@Override
	@Transactional
	public void setQuantity(UUID productId, Long userId, Integer quantity) {
		if (!productRepository.existById(productId)) {
			throw new ProductNotFoundException(productId);
		}

		CartItem cartItem = cartItemRepository.findByProductIdAndUserId(productId, userId)
				.orElseThrow(() -> new CartItemNotFoundException(productId));

		Product product = (Product) productRepository.findById(productId).orElseThrow();
		if (quantity > product.quantity()) {
			throw new ExceedsAvailableException(productId, quantity);
		}

		// Update the cart item quantity
		// cartItem.setQuantity(quantity);
		// cartItemRepository.save(cartItem);
	}
}
