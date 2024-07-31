package com.academy.orders.apirest.orders.controller;

import com.academy.orders.apirest.common.ErrorHandler;
import com.academy.orders.apirest.common.TestSecurityConfig;
import com.academy.orders.apirest.common.mapper.PageableDTOMapper;
import com.academy.orders.apirest.orders.mapper.OrderDTOMapper;
import com.academy.orders.apirest.orders.mapper.OrderFilterParametersDTOMapper;
import com.academy.orders.apirest.orders.mapper.OrderStatusInfoDTOMapper;
import com.academy.orders.apirest.orders.mapper.PageOrderDTOMapper;
import com.academy.orders.apirest.orders.mapper.UpdateOrderStatusRequestDTOMapper;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.order.dto.OrdersFilterParametersDto;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.usecase.GetAllOrdersUseCase;
import com.academy.orders.domain.order.usecase.GetOrderByIdUseCase;
import com.academy.orders.domain.order.usecase.UpdateOrderStatusUseCase;
import com.academy.orders_api_rest.generated.model.ManagerOrderDTO;
import com.academy.orders_api_rest.generated.model.OrderStatusInfoDTO;
import com.academy.orders_api_rest.generated.model.OrdersFilterParametersDTO;
import com.academy.orders_api_rest.generated.model.PageManagerOrderPreviewDTO;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static com.academy.orders.apirest.ModelUtils.getManagerOrderDTO;
import static com.academy.orders.apirest.ModelUtils.getOrder;
import static com.academy.orders.apirest.ModelUtils.getOrderStatusInfo;
import static com.academy.orders.apirest.ModelUtils.getOrderStatusInfoDTO;
import static com.academy.orders.apirest.ModelUtils.getOrdersFilterParametersDTO;
import static com.academy.orders.apirest.ModelUtils.getOrdersFilterParametersDTOParams;
import static com.academy.orders.apirest.ModelUtils.getOrdersFilterParametersDto;
import static com.academy.orders.apirest.ModelUtils.getPageManagerOrderPreviewDTO;
import static com.academy.orders.apirest.ModelUtils.getPageOf;
import static com.academy.orders.apirest.ModelUtils.getPageable;
import static com.academy.orders.apirest.ModelUtils.getPageableParams;
import static com.academy.orders.apirest.ModelUtils.getUpdateOrderStatusDto;
import static com.academy.orders.apirest.ModelUtils.getUpdateOrderStatusRequestDTO;
import static com.academy.orders.apirest.TestConstants.ROLE_MANAGER;
import static com.academy.orders.apirest.TestConstants.TEST_UUID;
import static com.academy.orders.apirest.TestConstants.UPDATE_ORDER_STATUS_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrdersManagementController.class)
@ContextConfiguration(classes = {OrdersManagementController.class})
@Import(value = {AopAutoConfiguration.class, TestSecurityConfig.class, ErrorHandler.class})
class OrdersManagementControllerTest {
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UpdateOrderStatusUseCase updateOrderStatusUseCase;
	@MockBean
	private GetOrderByIdUseCase getOrderByIdUseCase;
	@MockBean
	private PageableDTOMapper pageableDTOMapper;
	@MockBean
	private PageOrderDTOMapper pageOrderDTOMapper;
	@MockBean
	private GetAllOrdersUseCase getAllOrdersUseCase;
	@MockBean
	private OrderFilterParametersDTOMapper orderFilterParametersDTOMapper;
	@MockBean
	private OrderDTOMapper orderDTOMapper;
	@MockBean
	private OrderStatusInfoDTOMapper orderStatusInfoDTOMapper;
	@MockBean
	private UpdateOrderStatusRequestDTOMapper updateOrderStatusRequestDTOMapper;

	@Test
	@WithMockUser(authorities = ROLE_MANAGER)
	void getAllOrdersTest() throws Exception {
		// Given
		Long userId = 1L;
		PageableDTO pageableDTO = new PageableDTO();
		Pageable pageable = getPageable();
		Page<Order> orderPage = getPageOf(getOrder());
		OrdersFilterParametersDto orderFilterParametersDto = getOrdersFilterParametersDto();
		OrdersFilterParametersDTO orderFilterParametersDTO = getOrdersFilterParametersDTO();
		PageManagerOrderPreviewDTO pageOrderDTO = getPageManagerOrderPreviewDTO();

		when(pageableDTOMapper.fromDto(pageableDTO)).thenReturn(pageable);
		when(orderFilterParametersDTOMapper.fromDTO(orderFilterParametersDTO)).thenReturn(orderFilterParametersDto);
		when(getAllOrdersUseCase.getAllOrders(orderFilterParametersDto, pageable)).thenReturn(orderPage);
		when(pageOrderDTOMapper.toManagerDto(orderPage)).thenReturn(pageOrderDTO);

		// When
		MvcResult result = mockMvc
				.perform(get("/v1/management/orders", userId).contentType(MediaType.APPLICATION_JSON)
						.params(getPageableParams(pageableDTO.getPage(), pageableDTO.getSize(), pageableDTO.getSort()))
						.params(getOrdersFilterParametersDTOParams(orderFilterParametersDTO)))
				.andExpect(status().isOk()).andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		// Then

		verify(pageableDTOMapper).fromDto(pageableDTO);
		verify(orderFilterParametersDTOMapper).fromDTO(orderFilterParametersDTO);
		verify(getAllOrdersUseCase).getAllOrders(orderFilterParametersDto, pageable);
		assertEquals(pageOrderDTO, objectMapper.readValue(contentAsString, PageManagerOrderPreviewDTO.class));
	}

	@Test
	@WithMockUser(authorities = ROLE_MANAGER)
	void updateOrderStatusTest() throws Exception {
		var orderId = UUID.randomUUID();
		var updateOrderStatusRequestDTO = getUpdateOrderStatusRequestDTO();
		var updateOrderStatusDto = getUpdateOrderStatusDto();

		var orderStatusInfoDTO = getOrderStatusInfoDTO();
		var orderStatusInfo = getOrderStatusInfo();

		when(updateOrderStatusRequestDTOMapper.fromDTO(updateOrderStatusRequestDTO)).thenReturn(updateOrderStatusDto);
		when(updateOrderStatusUseCase.updateOrderStatus(orderId, updateOrderStatusDto, "user"))
				.thenReturn(orderStatusInfo);
		when(orderStatusInfoDTOMapper.toDTO(orderStatusInfo)).thenReturn(orderStatusInfoDTO);

		var request = objectMapper.writeValueAsString(updateOrderStatusRequestDTO);
		var result = mockMvc.perform(
				patch(UPDATE_ORDER_STATUS_URL, orderId).contentType(MediaType.APPLICATION_JSON).content(request))
				.andExpect(status().isOk()).andReturn();

		var contentAsString = result.getResponse().getContentAsString();
		assertEquals(orderStatusInfoDTO, objectMapper.readValue(contentAsString, OrderStatusInfoDTO.class));

		verify(updateOrderStatusRequestDTOMapper).fromDTO(updateOrderStatusRequestDTO);
		verify(updateOrderStatusUseCase).updateOrderStatus(orderId, updateOrderStatusDto, "user");
		verify(orderStatusInfoDTOMapper).toDTO(orderStatusInfo);

	}

	@Test
	@WithMockUser(authorities = ROLE_MANAGER)
	void getOrderByIdTest() throws Exception {
		// Given
		UUID orderId = TEST_UUID;
		String language = "uk";
		Order order = getOrder();
		ManagerOrderDTO orderDTO = getManagerOrderDTO();

		when(getOrderByIdUseCase.getOrderById(orderId, language)).thenReturn(order);
		when(orderDTOMapper.toManagerDto(order)).thenReturn(orderDTO);

		// When
		MvcResult result = mockMvc.perform(
				get("/v1/orders/{orderId}", orderId).contentType(MediaType.APPLICATION_JSON).param("lang", language))
				.andExpect(status().isOk()).andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		// Then
		verify(getOrderByIdUseCase).getOrderById(orderId, language);
		verify(orderDTOMapper).toManagerDto(order);
		assertEquals(orderDTO, objectMapper.readValue(contentAsString, ManagerOrderDTO.class));
	}
}
