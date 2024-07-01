package com.academy.orders.apirest.cart.controller;

import com.academy.orders.apirest.common.ErrorHandler;
import com.academy.orders.apirest.common.TestSecurityConfig;
import com.academy.orders.domain.cart.entity.CreateCartItemDTO;
import com.academy.orders.domain.cart.usecase.CreateCartItemByUserUseCase;
import com.academy.orders.domain.product.exception.ProductNotFoundException;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@WebMvcTest(controllers = CartItemController.class)
@ContextConfiguration(classes = {CartItemController.class})
@Import(value = {AopAutoConfiguration.class, TestSecurityConfig.class, ErrorHandler.class})
class CartItemControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CreateCartItemByUserUseCase cartItemByUserUseCase;

	@Test
	@WithMockUser(roles = "ADMIN")
	void testAddProductToCart() throws Exception {
		doNothing().when(cartItemByUserUseCase).create(any(CreateCartItemDTO.class));

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/cart/" + UUID.randomUUID() + "/" + 1L)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated());

		verify(cartItemByUserUseCase).create(any(CreateCartItemDTO.class));
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void testAddProductToCartThrowsNotFoundException() throws Exception {
		doThrow(ProductNotFoundException.class).when(cartItemByUserUseCase).create(any(CreateCartItemDTO.class));

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/cart/" + UUID.randomUUID() + "/" + 1L)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNotFound());

		verify(cartItemByUserUseCase).create(any(CreateCartItemDTO.class));
	}
}
