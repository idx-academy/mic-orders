package com.academy.orders.apirest.orders.controller;

import com.academy.orders.apirest.auth.validator.CheckAccountIdUseCaseImpl;
import com.academy.orders.apirest.common.ErrorHandler;
import com.academy.orders.apirest.common.TestSecurityConfig;
import com.academy.orders.apirest.common.mapper.PageableDTOMapper;
import com.academy.orders.apirest.orders.mapper.OrderDTOMapper;
import com.academy.orders.apirest.orders.mapper.OrderDTOMapperImpl;
import com.academy.orders.apirest.orders.mapper.PageOrderDTOMapper;
import com.academy.orders.domain.cart.exception.EmptyCartException;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.order.dto.CreateOrderDto;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.exception.InsufficientProductQuantityException;
import com.academy.orders.domain.order.usecase.CreateOrderUseCase;
import com.academy.orders.domain.product.usecase.GetOrdersByUserIdUseCase;
import com.academy.orders_api_rest.generated.model.PageOrderDTO;
import com.academy.orders_api_rest.generated.model.PageableDTO;
import com.academy.orders_api_rest.generated.model.PlaceOrderRequestDTO;
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
import org.springframework.test.web.servlet.MvcResult;

import static com.academy.orders.apirest.ModelUtils.getJwtRequest;
import static com.academy.orders.apirest.ModelUtils.getOrder;
import static com.academy.orders.apirest.ModelUtils.getPageOf;
import static com.academy.orders.apirest.ModelUtils.getPageOrderDTO;
import static com.academy.orders.apirest.ModelUtils.getPageable;
import static com.academy.orders.apirest.ModelUtils.getPageableParams;
import static com.academy.orders.apirest.ModelUtils.getPlaceOrderRequestDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrdersController.class)
@ContextConfiguration(classes = {OrdersController.class})
@Import(value = {OrderDTOMapperImpl.class, CheckAccountIdUseCaseImpl.class, AopAutoConfiguration.class, TestSecurityConfig.class, ErrorHandler.class})
class OrdersControllerTest {
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private CreateOrderUseCase createOrderUseCase;
	@MockBean
	private OrderDTOMapper mapper;
	@MockBean
	private PageableDTOMapper pageableDTOMapper;
	@MockBean
	private PageOrderDTOMapper pageOrderDTOMapper;
	@MockBean
	private GetOrdersByUserIdUseCase getOrdersByUserIdUseCase;

	@Test
	@SneakyThrows
	void testCreateOrderTest() {
		Long userId = 1L;
		String role = "ROLE_ADMIN";
		var orderId = UUID.randomUUID();

		when(mapper.toCreateOrderDto(any(PlaceOrderRequestDTO.class))).thenReturn(CreateOrderDto.builder().build());
		when(createOrderUseCase.createOrder(any(CreateOrderDto.class), anyLong())).thenReturn(orderId);

		mockMvc.perform(post("/v1/users/{id}/orders", userId).with(getJwtRequest(userId, role))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(getPlaceOrderRequestDTO()))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.orderId").value(orderId.toString()));

		verify(mapper).toCreateOrderDto(any(PlaceOrderRequestDTO.class));
		verify(createOrderUseCase).createOrder(any(CreateOrderDto.class), anyLong());
	}

	@Test
	@SneakyThrows
	void testCreateOrderThrowsEmptyCartExceptionTest() {
		Long userId = 1L;
		String role = "ROLE_ADMIN";

		when(mapper.toCreateOrderDto(any(PlaceOrderRequestDTO.class))).thenReturn(CreateOrderDto.builder().build());
		when(createOrderUseCase.createOrder(any(CreateOrderDto.class), anyLong())).thenThrow(new EmptyCartException());

		mockMvc.perform(post("/v1/users/{id}/orders", userId).with(getJwtRequest(userId, role))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(getPlaceOrderRequestDTO())))
				.andExpect(status().isBadRequest());

		verify(mapper).toCreateOrderDto(any(PlaceOrderRequestDTO.class));
		verify(createOrderUseCase).createOrder(any(CreateOrderDto.class), anyLong());
	}

	@Test
	@SneakyThrows
	void testCreateOrderThrowsInsufficientProductQuantityExceptionTest() {
		Long userId = 1L;
		String role = "ROLE_ADMIN";

		when(mapper.toCreateOrderDto(any(PlaceOrderRequestDTO.class))).thenReturn(CreateOrderDto.builder().build());

		when(createOrderUseCase.createOrder(any(CreateOrderDto.class), anyLong()))
				.thenThrow(new InsufficientProductQuantityException(UUID.randomUUID()));

		mockMvc.perform(post("/v1/users/{id}/orders", userId).with(getJwtRequest(userId, role))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(getPlaceOrderRequestDTO()))).andExpect(status().isConflict());
		verify(mapper).toCreateOrderDto(any(PlaceOrderRequestDTO.class));
		verify(createOrderUseCase).createOrder(any(CreateOrderDto.class), anyLong());
	}

	@Test
	@SneakyThrows
	void getOrdersByUserTest() {
		// Given
		Long userId = 1L;
		String role = "ROLE_USER";
		String language = "ua";
		PageableDTO pageableDTO = new PageableDTO();
		Pageable pageable = getPageable();
		Page<Order> orderPage = getPageOf(getOrder());
		PageOrderDTO pageOrderDTO = getPageOrderDTO();

		when(pageableDTOMapper.fromDto(pageableDTO)).thenReturn(pageable);
		when(getOrdersByUserIdUseCase.getOrdersByUserId(userId, language, pageable)).thenReturn(orderPage);
		when(pageOrderDTOMapper.toDto(orderPage)).thenReturn(pageOrderDTO);

		// When
		MvcResult result = mockMvc
				.perform(get("/v1/users/{id}/orders", userId).with(getJwtRequest(userId, role))
						.contentType(MediaType.APPLICATION_JSON).param("language", language)
						.params(getPageableParams(pageableDTO.getPage(), pageableDTO.getSize(), pageableDTO.getSort())))
				.andExpect(status().isOk()).andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		// Then
		assertEquals(pageOrderDTO, objectMapper.readValue(contentAsString, PageOrderDTO.class));
		verify(pageableDTOMapper).fromDto(pageableDTO);
		verify(getOrdersByUserIdUseCase).getOrdersByUserId(userId, language, pageable);
		verify(pageOrderDTOMapper).toDto(orderPage);
	}
}