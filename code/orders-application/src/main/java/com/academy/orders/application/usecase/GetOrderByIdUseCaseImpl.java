package com.academy.orders.application.usecase;

import java.util.UUID;

import com.academy.orders.domain.entity.Order;
import com.academy.orders.domain.repository.OrderRepository;
import com.academy.orders.domain.usecase.GetOrderByIdUseCase;
import com.academy.orders.domain.usecase.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetOrderByIdUseCaseImpl implements GetOrderByIdUseCase {

	private final OrderRepository orderRepository;

	@Override
	public Order getOrderById(UUID id) {
		return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
	}
}
