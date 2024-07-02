package com.academy.orders.apirest.cart.controller;

import com.academy.orders.domain.cart.entity.CreateCartItemDTO;
import com.academy.orders.domain.cart.usecase.CreateCartItemByUserUseCase;
import com.academy.orders_api_rest.generated.api.CartApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class CartItemController implements CartApi {
	private final CreateCartItemByUserUseCase cartItemByUserUseCase;

	@Override
	public void addProductToCart(UUID productId, Long userId) {
		cartItemByUserUseCase.create(new CreateCartItemDTO(productId, userId, 1));
	}
}
