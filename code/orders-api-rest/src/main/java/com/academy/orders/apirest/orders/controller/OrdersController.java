package com.academy.orders.apirest.orders.controller;

import com.academy.orders.apirest.orders.mapper.OrderDTOMapper;
import com.academy.orders.domain.order.usecase.CreateOrderUseCase;
import com.academy.orders.domain.order.usecase.GetOrderByIdUseCase;
import com.academy.orders_api_rest.generated.api.OrdersApi;
import com.academy.orders_api_rest.generated.model.OrderDTO;
import com.academy.orders_api_rest.generated.model.PlaceOrderRequestDTO;
import com.academy.orders_api_rest.generated.model.PlaceOrderResponseDTO;
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
public class OrdersController implements OrdersApi {

	private final GetOrderByIdUseCase getOrderByIdUseCase;
	private final CreateOrderUseCase createOrderUseCase;

	private final OrderDTOMapper mapper;

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public OrderDTO getOrderById(UUID orderId) {
		log.debug("Get order by id {}", orderId);

		final var order = getOrderByIdUseCase.getOrderById(orderId);
		return mapper.toDto(order);
	}

	@Override
	public PlaceOrderResponseDTO placeOrder(PlaceOrderRequestDTO placeOrderRequestDTO) {
		var userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		var id = createOrderUseCase.createOrder(mapper.toCreateOrderDto(placeOrderRequestDTO), userEmail);
		return new PlaceOrderResponseDTO().orderId(id);
	}
}
