package com.academy.orders.apirest.orders.controller;

import com.academy.orders.apirest.common.ErrorHandler;
import com.academy.orders.apirest.common.TestSecurityConfig;
import com.academy.orders.apirest.common.mapper.PageableDTOMapper;
import com.academy.orders.apirest.orders.mapper.OrderFilterParametersDTOMapper;
import com.academy.orders.apirest.orders.mapper.OrderStatusMapper;
import com.academy.orders.apirest.orders.mapper.PageOrderDTOMapper;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.order.dto.OrdersFilterParametersDto;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.usecase.GetAllOrdersUseCase;
import com.academy.orders.domain.order.usecase.UpdateOrderStatusUseCase;
import com.academy.orders_api_rest.generated.model.OrderStatusDTO;
import com.academy.orders_api_rest.generated.model.OrdersFilterParametersDTO;
import com.academy.orders_api_rest.generated.model.PageManagerOrderDTO;
import com.academy.orders_api_rest.generated.model.PageableDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MvcResult;

import static com.academy.orders.apirest.ModelUtils.getJwtRequest;
import static com.academy.orders.apirest.ModelUtils.getOrder;
import static com.academy.orders.apirest.ModelUtils.getOrdersFilterParametersDTO;
import static com.academy.orders.apirest.ModelUtils.getOrdersFilterParametersDto;
import static com.academy.orders.apirest.ModelUtils.getOrdersFilterParametersDTOParams;
import static com.academy.orders.apirest.ModelUtils.getPageManagerOrderDTO;
import static com.academy.orders.apirest.ModelUtils.getPageOf;
import static com.academy.orders.apirest.ModelUtils.getPageable;
import static com.academy.orders.apirest.ModelUtils.getPageableParams;
import static com.academy.orders.apirest.TestConstants.ROLE_MANAGER;
import static com.academy.orders.apirest.TestConstants.UPDATE_ORDER_STATUS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrdersManagementController.class)
@ContextConfiguration(classes = {OrdersManagementController.class})
@Import(value = {AopAutoConfiguration.class, TestSecurityConfig.class, ErrorHandler.class})
public class OrdersManagementControllerTest {
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UpdateOrderStatusUseCase updateOrderStatusUseCase;
	@MockBean
	private PageableDTOMapper pageableDTOMapper;
	@MockBean
	private PageOrderDTOMapper pageOrderDTOMapper;
	@MockBean
	private GetAllOrdersUseCase getAllOrdersUseCase;
	@MockBean
	private OrderFilterParametersDTOMapper orderFilterParametersDTOMapper;
	@MockBean
	private OrderStatusMapper orderStatusMapper;

	@Test
	void getAllOrdersTest() throws Exception {
		// Given
		Long userId = 1L;
		String language = "ua";
		PageableDTO pageableDTO = new PageableDTO();
		Pageable pageable = getPageable();
		Page<Order> orderPage = getPageOf(getOrder());
		OrdersFilterParametersDto orderFilterParametersDto = getOrdersFilterParametersDto();
		OrdersFilterParametersDTO orderFilterParametersDTO = getOrdersFilterParametersDTO();
		PageManagerOrderDTO pageOrderDTO = getPageManagerOrderDTO();

		when(pageableDTOMapper.fromDto(pageableDTO)).thenReturn(pageable);
		when(orderFilterParametersDTOMapper.fromDTO(orderFilterParametersDTO)).thenReturn(orderFilterParametersDto);
		when(getAllOrdersUseCase.getAllOrders(orderFilterParametersDto, language, pageable)).thenReturn(orderPage);
		when(pageOrderDTOMapper.toManagerDto(orderPage)).thenReturn(pageOrderDTO);

		// When
		MvcResult result = mockMvc
				.perform(get("/v1/management/orders", userId).with(getJwtRequest(userId, ROLE_MANAGER))
						.contentType(MediaType.APPLICATION_JSON).param("lang", language)
						.params(getPageableParams(pageableDTO.getPage(), pageableDTO.getSize(), pageableDTO.getSort()))
						.params(getOrdersFilterParametersDTOParams(orderFilterParametersDTO)))
				.andExpect(status().isOk()).andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		// Then

		verify(pageableDTOMapper).fromDto(pageableDTO);
		verify(orderFilterParametersDTOMapper).fromDTO(orderFilterParametersDTO);
		verify(getAllOrdersUseCase).getAllOrders(orderFilterParametersDto, language, pageable);
		assertEquals(pageOrderDTO, objectMapper.readValue(contentAsString, PageManagerOrderDTO.class));
	}

	@Test
	void updateOrderStatusTest() throws Exception {
		var orderId = UUID.randomUUID();
		var status = OrderStatusDTO.COMPLETED;

		doNothing().when(updateOrderStatusUseCase).updateOrderStatus(any(UUID.class), any());

		mockMvc.perform(patch(UPDATE_ORDER_STATUS, orderId).with(getJwtRequest(1L, ROLE_MANAGER))
				.param("orderStatus", status.toString()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		verify(updateOrderStatusUseCase).updateOrderStatus(orderId, orderStatusMapper.fromDTO(status));
	}
}