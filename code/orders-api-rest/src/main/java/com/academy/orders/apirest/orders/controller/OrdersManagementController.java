package com.academy.orders.apirest.orders.controller;

import com.academy.orders.apirest.common.mapper.PageableDTOMapper;
import com.academy.orders.apirest.orders.mapper.OrderDTOMapper;
import com.academy.orders.apirest.orders.mapper.OrderFilterParametersDTOMapper;
import com.academy.orders.apirest.orders.mapper.OrderStatusMapper;
import com.academy.orders.apirest.orders.mapper.PageOrderDTOMapper;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.order.dto.OrdersFilterParametersDto;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.usecase.GetAllOrdersUseCase;
import com.academy.orders.domain.order.usecase.GetOrderByIdUseCase;
import com.academy.orders.domain.order.usecase.UpdateOrderStatusUseCase;
import com.academy.orders_api_rest.generated.api.OrdersManagementApi;
import com.academy.orders_api_rest.generated.model.ManagerOrderDTO;
import com.academy.orders_api_rest.generated.model.OrderStatusDTO;
import com.academy.orders_api_rest.generated.model.OrdersFilterParametersDTO;
import com.academy.orders_api_rest.generated.model.PageManagerOrderDTO;
import com.academy.orders_api_rest.generated.model.PageableDTO;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class OrdersManagementController implements OrdersManagementApi {
	private final UpdateOrderStatusUseCase updateOrderStatusUseCase;
	private final GetAllOrdersUseCase getAllOrdersUseCase;
	private final GetOrderByIdUseCase getOrderByIdUseCase;
	private final PageableDTOMapper pageableDTOMapper;
	private final PageOrderDTOMapper pageOrderDTOMapper;
	private final OrderFilterParametersDTOMapper orderFilterParametersDTOMapper;
	private final OrderStatusMapper orderStatusMapper;
	private final OrderDTOMapper orderDTOMapper;

	@Override
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGER')")
	public PageManagerOrderDTO getAllOrders(OrdersFilterParametersDTO ordersFilter, String lang, PageableDTO pageable) {
		Pageable pageableDomain = pageableDTOMapper.fromDto(pageable);
		OrdersFilterParametersDto filterParametersDto = orderFilterParametersDTOMapper.fromDTO(ordersFilter);
		Page<Order> ordersByUserId = getAllOrdersUseCase.getAllOrders(filterParametersDto, lang, pageableDomain);
		return pageOrderDTOMapper.toManagerDto(ordersByUserId);
	}

	@Override
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
	public void updateOrderStatus(UUID orderId, OrderStatusDTO orderStatusDTO) {
		updateOrderStatusUseCase.updateOrderStatus(orderId, orderStatusMapper.fromDTO(orderStatusDTO),
				getCurrentAccountEmail());
	}

	@Override
	@PreAuthorize("hasAuthority('ROLE_MANAGER')")
	public ManagerOrderDTO getOrderById(UUID id, String lang) {
		Order order = getOrderByIdUseCase.getOrderById(id, lang);
		return orderDTOMapper.toManagerDto(order);
	}

	private String getCurrentAccountEmail() {
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
}
