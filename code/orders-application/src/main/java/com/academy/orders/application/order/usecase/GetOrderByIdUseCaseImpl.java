package com.academy.orders.application.order.usecase;

import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.exception.OrderNotFoundException;
import com.academy.orders.domain.order.repository.OrderImageRepository;
import com.academy.orders.domain.order.repository.OrderRepository;
import com.academy.orders.domain.order.usecase.CalculateOrderTotalPriceUseCase;
import com.academy.orders.domain.order.usecase.GetOrderByIdUseCase;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetOrderByIdUseCaseImpl implements GetOrderByIdUseCase {
	private final CalculateOrderTotalPriceUseCase calculateOrderTotalPriceUseCase;
	private final OrderRepository orderRepository;
	private final OrderImageRepository orderImageRepository;

	@Override
	public Order getOrderById(UUID id, String language) {
		return calculateOrderTotalPriceUseCase.calculateTotalPriceFor(
				orderRepository.findById(id, language).map(orderImageRepository::loadImageForProductInOrder)
						.orElseThrow(() -> new OrderNotFoundException(id)));
	}
}
