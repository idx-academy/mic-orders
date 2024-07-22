package com.academy.orders.application.order.usecase;

import java.util.UUID;

import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.repository.OrderRepository;
import com.academy.orders.domain.order.usecase.GetOrderByIdUseCase;
import com.academy.orders.domain.order.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetOrderByIdUseCaseImpl implements GetOrderByIdUseCase {

	private final OrderRepository orderRepository;

	@Override
	public Order getOrderById(UUID id, String language) {
		return orderRepository.findById(id, language).orElseThrow(() -> new OrderNotFoundException(id));
	}
}
