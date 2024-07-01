package com.academy.orders.apirest.orders.controller;

import com.academy.orders.apirest.common.ErrorHandler;
import com.academy.orders.apirest.common.TestSecurityConfig;
import com.academy.orders.apirest.orders.mapper.OrderDTOMapper;
import com.academy.orders.apirest.orders.mapper.OrderDTOMapperImpl;
import com.academy.orders.domain.cart.exception.EmptyCartException;
import com.academy.orders.domain.order.dto.CreateOrderDto;
import com.academy.orders.domain.order.exception.InsufficientProductQuantityException;
import com.academy.orders.domain.order.usecase.CreateOrderUseCase;
import com.academy.orders_api_rest.generated.model.PlaceOrderRequestDTO;
import java.util.UUID;
import lombok.SneakyThrows;
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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static com.academy.orders.apirest.ModelUtils.getPlaceOrderRequestDTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrdersController.class)
@ContextConfiguration(classes = {OrdersController.class})
@Import(value = {OrderDTOMapperImpl.class, AopAutoConfiguration.class, TestSecurityConfig.class, ErrorHandler.class})
class OrdersControllerTest {
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private CreateOrderUseCase createOrderUseCase;
	@MockBean
	private OrderDTOMapper mapper;

	@Test
	@WithMockUser(roles = "ADMIN")
	@SneakyThrows
	void testCreateOrder() {
		var orderId = UUID.randomUUID();

		when(mapper.toCreateOrderDto(any(PlaceOrderRequestDTO.class))).thenReturn(CreateOrderDto.builder().build());
		when(createOrderUseCase.createOrder(any(CreateOrderDto.class), anyLong())).thenReturn(orderId);

		mockMvc.perform(post("/v1/users/{id}/orders", 1L).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(getPlaceOrderRequestDTO()))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.orderId").value(orderId.toString()));
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@SneakyThrows
	void testCreateOrderThrowsEmptyCartException() {
		when(mapper.toCreateOrderDto(any(PlaceOrderRequestDTO.class))).thenReturn(CreateOrderDto.builder().build());

		when(createOrderUseCase.createOrder(any(CreateOrderDto.class), anyLong())).thenThrow(new EmptyCartException());

		mockMvc.perform(post("/v1/users/{id}/orders", 1L).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(getPlaceOrderRequestDTO())))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@SneakyThrows
	void testCreateOrderThrows() {
		when(mapper.toCreateOrderDto(any(PlaceOrderRequestDTO.class))).thenReturn(CreateOrderDto.builder().build());

		when(createOrderUseCase.createOrder(any(CreateOrderDto.class), anyLong()))
				.thenThrow(new InsufficientProductQuantityException(UUID.randomUUID()));

		mockMvc.perform(post("/v1/users/{id}/orders", 1L).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(getPlaceOrderRequestDTO())))
				.andExpect(status().isBadRequest());
	}

}