package com.academy.orders.application.cart.usecase;

import com.academy.orders.domain.cart.entity.CreateCartItemDTO;
import com.academy.orders.domain.cart.exception.CartItemAlreadyExists;
import com.academy.orders.domain.cart.repository.CartItemRepository;
import com.academy.orders.domain.cart.usecase.CreateCartItemByUserUseCase;
import com.academy.orders.domain.product.exception.ProductNotFoundException;
import com.academy.orders.domain.product.repository.ProductRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCartItemByUserUseCaseUseCaseImpl implements CreateCartItemByUserUseCase {
	private final CartItemRepository cartItemRepository;
	private final ProductRepository productRepository;

	@Override
	public void create(CreateCartItemDTO cartItem) {
		checkIfProductExistsById(cartItem.productId());
		checkIsProductAddedToCart(cartItem);
		saveCartItem(cartItem);
	}

	private void checkIfProductExistsById(UUID uuid) {
		if (!productRepository.existById(uuid)) {
			throw new ProductNotFoundException(uuid);
		}
	}

	private void checkIsProductAddedToCart(CreateCartItemDTO cartItem) {
		boolean isPresent = cartItemRepository.existsByProductIdAndUserId(cartItem.productId(), cartItem.userId());
		if (isPresent) {
			throw new CartItemAlreadyExists(cartItem.productId());
		}
	}

	private void saveCartItem(CreateCartItemDTO cartItem) {
		cartItemRepository.save(cartItem);
	}
}
