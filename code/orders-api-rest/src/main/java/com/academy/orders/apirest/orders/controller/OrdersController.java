package com.academy.orders.apirest.orders.controller;

import com.academy.orders.apirest.common.mapper.PageableDTOMapper;
import com.academy.orders.apirest.orders.mapper.PageOrderDTOMapper;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.product.usecase.GetOrdersByUserIdUseCase;
import com.academy.orders_api_rest.generated.model.PageOrderDTO;
import com.academy.orders_api_rest.generated.model.PageableDTO;
import java.util.UUID;

import com.academy.orders.apirest.orders.mapper.OrderDTOMapper;
import com.academy.orders.domain.order.usecase.GetOrderByIdUseCase;
import com.academy.orders_api_rest.generated.api.OrdersApi;
import com.academy.orders_api_rest.generated.model.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class OrdersController implements OrdersApi {
	private final GetOrderByIdUseCase getOrderByIdUseCase;
	private final GetOrdersByUserIdUseCase getOrdersByUserIdUseCase;
	private final OrderDTOMapper mapper;
	private final PageableDTOMapper pageableDTOMapper;
	private final PageOrderDTOMapper pageOrderDTOMapper;

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public OrderDTO getOrderById(UUID orderId) {
		log.debug("Get order by id {}", orderId);
		final var order = getOrderByIdUseCase.getOrderById(orderId);
		return mapper.toDto(order);
	}

	@Override
	public PageOrderDTO getOrdersByUser(Long userId, String language, PageableDTO pageable) {
		Pageable pageableDomain = pageableDTOMapper.fromDto(pageable);
		Page<Order> ordersByUserId = getOrdersByUserIdUseCase.getOrdersByUserId(userId, language, pageableDomain);
		return pageOrderDTOMapper.toDto(ordersByUserId);
	}
}
