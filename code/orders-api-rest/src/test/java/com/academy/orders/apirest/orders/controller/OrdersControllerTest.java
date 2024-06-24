package com.academy.orders.apirest.orders.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import com.academy.orders.apirest.common.TestSecurityConfig;
import com.academy.orders.apirest.orders.mapper.OrderDTOMapperImpl;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.usecase.GetOrderByIdUseCase;
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

@WebMvcTest(OrdersController.class)
@ContextConfiguration(classes = {OrdersController.class})
@Import(value = {OrderDTOMapperImpl.class, AopAutoConfiguration.class, TestSecurityConfig.class})
class OrdersControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GetOrderByIdUseCase getOrderByIdUseCase;

	@Test
	@WithMockUser(roles = "ADMIN")
	void testGetOrderById() throws Exception {
		// given
		var orderId = UUID.randomUUID();
		when(getOrderByIdUseCase.getOrderById(orderId)).thenReturn(Order.builder().id(orderId).build());

		// when
		mockMvc.perform(
				MockMvcRequestBuilders.get("/v1/orders/{orderId}", orderId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(orderId.toString()));

		// then
		verify(getOrderByIdUseCase).getOrderById(orderId);
	}

	@Test
	@WithMockUser(authorities = "USER")
	void testGetOrderById_forbidden() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/orders/{orderId}", UUID.randomUUID())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isForbidden());
	}
}