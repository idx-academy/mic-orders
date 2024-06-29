package com.academy.orders.application.cart.usecase;

import com.academy.orders.domain.cart.entity.CreateCartItemDTO;
import com.academy.orders.domain.cart.exception.CartItemAlreadyExistsException;
import com.academy.orders.domain.cart.repository.CartItemRepository;
import com.academy.orders.domain.cart.usecase.CreateCartItemByUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor
public class CreateCartItemByUserUseCaseUseCaseImpl implements CreateCartItemByUserUseCase {
	private final CartItemRepository cartItemRepository;

	@Override
	public void create(CreateCartItemDTO cartItem) {
		if (TRUE.equals(cartItemRepository.existsByProductIdAndUserId(cartItem.productId(), cartItem.userId()))) {
			throw new CartItemAlreadyExistsException(cartItem.productId());
		}
		cartItemRepository.save(cartItem);
	}
}
