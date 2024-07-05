package com.academy.orders.application.cart.usecase;

import com.academy.orders.domain.cart.exception.CartItemNotFoundException;
import com.academy.orders.domain.cart.repository.CartItemRepository;
import com.academy.orders.domain.cart.usecase.DeleteProductFromCartUseCase;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteProductFromCartUseCaseImpl implements DeleteProductFromCartUseCase {
	private final CartItemRepository cartItemRepository;

	@Override
	@Transactional
	public void deleteProductFromCart(Long userId, UUID productId) {
		checkIsProductAddedToCart(userId, productId);
		deleteCartItem(userId, productId);
	}

	private void checkIsProductAddedToCart(Long userId, UUID productId) {
		boolean isPresent = cartItemRepository.existsByProductIdAndUserId(productId, userId);
		if (!isPresent) {
			throw new CartItemNotFoundException(productId);
		}
	}

	private void deleteCartItem(Long userId, UUID productId) {
		cartItemRepository.deleteCartItemByAccountAndProductIds(userId, productId);
	}

}
