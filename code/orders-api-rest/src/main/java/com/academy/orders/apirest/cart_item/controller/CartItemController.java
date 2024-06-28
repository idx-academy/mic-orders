package com.academy.orders.apirest.cart_item.controller;

import com.academy.orders.application.cart_item.usecase.CreateCartItemByUserUseCaseImpl;
import com.academy.orders.domain.cart_item.entity.CreateCartItemDTO;
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
	private final CreateCartItemByUserUseCaseImpl cartItemByUserUseCase;

	@Override
	public void addProductToCart(UUID productId, Long userId) {
		CreateCartItemDTO createCartItemDTO = new CreateCartItemDTO(productId, userId, 1);
		cartItemByUserUseCase.create(createCartItemDTO);
	}
}
