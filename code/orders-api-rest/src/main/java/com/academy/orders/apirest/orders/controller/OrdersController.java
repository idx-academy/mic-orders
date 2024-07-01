package com.academy.orders.apirest.orders.controller;

import com.academy.orders.apirest.orders.mapper.OrderDTOMapper;
import com.academy.orders.domain.order.usecase.CreateOrderUseCase;
import com.academy.orders.domain.order.usecase.GetOrderByIdUseCase;
import com.academy.orders_api_rest.generated.api.OrdersApi;
import com.academy.orders_api_rest.generated.model.PlaceOrderRequestDTO;
import com.academy.orders_api_rest.generated.model.PlaceOrderResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
	public PlaceOrderResponseDTO placeOrder(PlaceOrderRequestDTO placeOrderRequestDTO) {
		var userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		var id = createOrderUseCase.createOrder(mapper.toCreateOrderDto(placeOrderRequestDTO), userEmail);
		return new PlaceOrderResponseDTO().orderId(id);
	}
}
