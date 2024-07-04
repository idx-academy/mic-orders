package com.academy.orders.application.order.usecase;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.repository.OrderRepository;
import com.academy.orders.domain.product.usecase.GetOrdersByUserIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetOrdersByUserIdUseCaseImpl implements GetOrdersByUserIdUseCase {
	private final OrderRepository orderRepository;
	@Override
	public Page<Order> getOrdersByUserId(Long id, String language, Pageable pageable) {
		return orderRepository.findAllByUserId(id, language, pageable);
	}
}
