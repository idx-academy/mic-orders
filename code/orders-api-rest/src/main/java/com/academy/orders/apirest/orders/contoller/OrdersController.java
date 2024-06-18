package com.academy.orders.apirest.orders.contoller;

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

	private final OrderDTOMapper mapper;

	@Override
	@PreAuthorize("hasRole('USER')")
	public OrderDTO getOrderById(UUID orderId) {
		log.debug("Get order by id {}", orderId);

		final var order = getOrderByIdUseCase.getOrderById(orderId);
		return mapper.toDto(order);
	}
}
