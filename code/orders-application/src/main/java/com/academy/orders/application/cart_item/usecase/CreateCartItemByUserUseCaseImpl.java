package com.academy.orders.application.cart_item.usecase;

import com.academy.orders.domain.cart_item.entity.CreateCartItemDTO;
import com.academy.orders.domain.cart_item.exception.CartItemAlreadyExistsException;
import com.academy.orders.domain.cart_item.repository.CartItemRepository;
import com.academy.orders.domain.cart_item.usecase.CreateCartItemByUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor
public class CreateCartItemByUserUseCaseImpl implements CreateCartItemByUser {
	private final CartItemRepository cartItemRepository;

	@Override
	public void create(CreateCartItemDTO cartItem) {
		if (TRUE.equals(cartItemRepository.existsByProductIdAndUserId(cartItem.productId(), cartItem.userId()))) {
			throw new CartItemAlreadyExistsException(cartItem.productId());
		}
		cartItemRepository.save(cartItem);
	}
}
