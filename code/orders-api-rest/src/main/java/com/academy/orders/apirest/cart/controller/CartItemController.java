package com.academy.orders.apirest.cart.controller;

import com.academy.orders.domain.cart.entity.CreateCartItemDTO;
import com.academy.orders.domain.cart.usecase.CreateCartItemByUserUseCase;
import com.academy.orders.domain.cart.usecase.DeleteProductFromCartUseCase;
import com.academy.orders_api_rest.generated.api.CartApi;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class CartItemController implements CartApi {
	private final CreateCartItemByUserUseCase cartItemByUserUseCase;
	private final DeleteProductFromCartUseCase deleteProductFromCartUseCase;

	@Override
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') || @checkAccountIdUseCaseImpl.hasSameId(#userId)")
	public void addProductToCart(UUID productId, Long userId) {
		cartItemByUserUseCase.create(new CreateCartItemDTO(productId, userId, 1));
	}

	@Override
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') || @checkAccountIdUseCaseImpl.hasSameId(#userId)")
	public void deleteProductFromCart(Long userId, UUID productId) {
		deleteProductFromCartUseCase.deleteProductFromCart(userId, productId);
	}
}
