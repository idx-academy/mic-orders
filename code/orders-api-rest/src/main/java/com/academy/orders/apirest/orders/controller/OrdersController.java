package com.academy.orders.apirest.orders.controller;

import com.academy.orders.apirest.orders.mapper.OrderDTOMapper;
import com.academy.orders.domain.order.usecase.CreateOrderUseCase;
import com.academy.orders_api_rest.generated.api.OrdersApi;
import com.academy.orders_api_rest.generated.model.PlaceOrderRequestDTO;
import com.academy.orders_api_rest.generated.model.PlaceOrderResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class OrdersController implements OrdersApi {
	private final CreateOrderUseCase createOrderUseCase;

	private final OrderDTOMapper mapper;

	@Override
	public PlaceOrderResponseDTO placeOrder(Long userId, PlaceOrderRequestDTO placeOrderRequestDTO) {
		var id = createOrderUseCase.createOrder(mapper.toCreateOrderDto(placeOrderRequestDTO), userId);
		return new PlaceOrderResponseDTO().orderId(id);
	}
}
