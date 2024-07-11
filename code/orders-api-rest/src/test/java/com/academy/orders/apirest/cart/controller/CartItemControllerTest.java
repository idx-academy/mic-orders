package com.academy.orders.apirest.cart.controller;

import com.academy.orders.apirest.ModelUtils;
import com.academy.orders.apirest.auth.validator.CheckAccountIdUseCaseImpl;
import com.academy.orders.apirest.cart.mapper.CartItemDTOMapper;
import com.academy.orders.apirest.common.ErrorHandler;
import com.academy.orders.apirest.common.TestSecurityConfig;
import com.academy.orders.domain.cart.dto.CartResponseDto;
import com.academy.orders.domain.cart.entity.CreateCartItemDTO;
import com.academy.orders.domain.cart.exception.CartItemNotFoundException;
import com.academy.orders.domain.cart.usecase.CreateCartItemByUserUseCase;
import com.academy.orders.domain.cart.usecase.DeleteProductFromCartUseCase;
import com.academy.orders.domain.cart.usecase.GetCartItemsUseCase;
import com.academy.orders.domain.product.exception.ProductNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static com.academy.orders.apirest.ModelUtils.getJwtRequest;
import static com.academy.orders.apirest.TestConstants.ROLE_ADMIN;
import static com.academy.orders.apirest.TestConstants.ROLE_USER;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CartItemController.class)
@ContextConfiguration(classes = {CartItemController.class})
@Import(value = {AopAutoConfiguration.class, TestSecurityConfig.class, ErrorHandler.class,
		CheckAccountIdUseCaseImpl.class})
class CartItemControllerTest {
	private final UUID productId = UUID.randomUUID();
	private final Long userId = 1L;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private CreateCartItemByUserUseCase cartItemByUserUseCase;

	@MockBean
	private DeleteProductFromCartUseCase deleteProductFromCartUseCase;

	@MockBean
	private GetCartItemsUseCase getCartItemsUseCase;

	@MockBean
	private CartItemDTOMapper cartItemDTOMapper;

	@Test
	@SneakyThrows
	void addProductToCartTest() {
		doNothing().when(cartItemByUserUseCase).create(any(CreateCartItemDTO.class));

		mockMvc.perform(post("/v1/users/{userId}/cart/{productId}", userId, productId)
				.contentType(MediaType.APPLICATION_JSON).with(getJwtRequest(userId, ROLE_USER)))
				.andExpect(status().isCreated());

		verify(cartItemByUserUseCase).create(any(CreateCartItemDTO.class));
	}

	@Test
	@SneakyThrows
	void addProductToCartThrowsNotFoundExceptionTest() {
		doThrow(ProductNotFoundException.class).when(cartItemByUserUseCase).create(any(CreateCartItemDTO.class));

		mockMvc.perform(post("/v1/users/{userId}/cart/{productId}", userId, productId)
				.contentType(MediaType.APPLICATION_JSON).with(getJwtRequest(userId, ROLE_ADMIN)))
				.andExpect(status().isNotFound());

		verify(cartItemByUserUseCase).create(any(CreateCartItemDTO.class));
	}

	@Test
	@SneakyThrows
	void deleteProductFromCartTest() {
		doNothing().when(deleteProductFromCartUseCase).deleteProductFromCart(userId, productId);

		mockMvc.perform(delete("/v1/users/{userId}/cart/items/{productId}", userId, productId)
				.with(getJwtRequest(userId, ROLE_ADMIN))).andExpect(status().isNoContent());

		verify(deleteProductFromCartUseCase).deleteProductFromCart(anyLong(), any(UUID.class));
	}
	@Test
	@SneakyThrows
	void deleteProductFromCartThrowsNotFoundExceptionTest() {
		doThrow(CartItemNotFoundException.class).when(deleteProductFromCartUseCase).deleteProductFromCart(userId,
				productId);

		mockMvc.perform(delete("/v1/users/{userId}/cart/items/{productId}", userId, productId)
				.with(getJwtRequest(userId, ROLE_ADMIN))).andExpect(status().isNotFound());

		verify(deleteProductFromCartUseCase).deleteProductFromCart(anyLong(), any(UUID.class));
	}

	@Test
	@SneakyThrows
	void getCartItemsTest() {
		var lang = "ua";
		var cartResponseDto = CartResponseDto.builder().build();
		var cartItemsResponseDTO = ModelUtils.getCartItemResponseDto();

		when(getCartItemsUseCase.getCartItems(anyLong(), anyString())).thenReturn(cartResponseDto);
		when(cartItemDTOMapper.toCartItemsResponseDTO(any(CartResponseDto.class))).thenReturn(cartItemsResponseDTO);

		mockMvc.perform(get("/v1/users/{userId}/cart/items", userId).param("lang", lang)
				.with(getJwtRequest(userId, ROLE_ADMIN))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(cartItemsResponseDTO)));

		verify(getCartItemsUseCase).getCartItems(anyLong(), anyString());
		verify(cartItemDTOMapper).toCartItemsResponseDTO(any(CartResponseDto.class));
	}
}
