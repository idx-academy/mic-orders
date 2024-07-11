package com.academy.orders.application.cart.usecase;

import com.academy.orders.domain.cart.dto.QuantityCheckResponseDto;
import com.academy.orders.domain.cart.repository.CartItemRepository;
import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.cart.usecase.CheckProductQuantityUseCase;
import com.academy.orders.domain.product.exception.ProductNotFoundException;
import com.academy.orders.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckProductQuantityUseCaseImpl implements CheckProductQuantityUseCase {
	private final CartItemRepository cartItemRepository;
	private final ProductRepository productRepository;

	@Override
	public QuantityCheckResponseDto checkQuantity(Long userId, UUID productId, int quantity) {
		var productQuantity = productRepository.findQuantityById(productId)
				.orElseThrow(() -> new ProductNotFoundException(productId));

		var cartItem = cartItemRepository.findByProductIdAndUserId(productId, userId);
		int availableQuantity = productQuantity - cartItem.map(CartItem::quantity).orElse(0);

		boolean isAvailable = availableQuantity >= quantity;

		return QuantityCheckResponseDto.builder().isAvailable(isAvailable).availableQuantity(availableQuantity).build();
	}
}