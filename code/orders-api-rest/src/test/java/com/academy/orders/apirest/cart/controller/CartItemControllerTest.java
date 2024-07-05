package com.academy.orders.apirest.cart.controller;

import com.academy.orders.apirest.auth.validator.CheckAccountIdUseCaseImpl;
import com.academy.orders.apirest.common.ErrorHandler;
import com.academy.orders.apirest.common.TestSecurityConfig;
import com.academy.orders.domain.cart.entity.CreateCartItemDTO;
import com.academy.orders.domain.cart.usecase.CreateCartItemByUserUseCase;
import com.academy.orders.domain.cart.usecase.DeleteProductFromCartUseCase;
import com.academy.orders.domain.product.exception.ProductNotFoundException;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.academy.orders.apirest.ModelUtils.getJwtRequest;
import static com.academy.orders.apirest.TestConstants.ROLE_ADMIN;
import static com.academy.orders.apirest.TestConstants.ROLE_USER;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = CartItemController.class)
@ContextConfiguration(classes = {CartItemController.class})
@Import(value = {AopAutoConfiguration.class, TestSecurityConfig.class, ErrorHandler.class,
		CheckAccountIdUseCaseImpl.class})
class CartItemControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CreateCartItemByUserUseCase cartItemByUserUseCase;

	@MockBean
	private DeleteProductFromCartUseCase deleteProductFromCartUseCase;

	@Test
	void testAddProductToCart() throws Exception {
		var productId = UUID.randomUUID();
		var userId = 1L;

		doNothing().when(cartItemByUserUseCase).create(any(CreateCartItemDTO.class));

		mockMvc.perform(post("/v1/users/{userId}/cart/{productId}", userId, productId)
				.contentType(MediaType.APPLICATION_JSON).with(getJwtRequest(userId, ROLE_USER)))
				.andExpect(MockMvcResultMatchers.status().isCreated());

		verify(cartItemByUserUseCase).create(any(CreateCartItemDTO.class));
	}

	@Test
	void testAddProductToCartThrowsNotFoundException() throws Exception {
		var productId = UUID.randomUUID();
		var userId = 1L;

		doThrow(ProductNotFoundException.class).when(cartItemByUserUseCase).create(any(CreateCartItemDTO.class));

		mockMvc.perform(post("/v1/users/{userId}/cart/{productId}", userId, productId)
				.contentType(MediaType.APPLICATION_JSON).with(getJwtRequest(userId, ROLE_ADMIN)))
				.andExpect(MockMvcResultMatchers.status().isNotFound());

		verify(cartItemByUserUseCase).create(any(CreateCartItemDTO.class));
	}
}
