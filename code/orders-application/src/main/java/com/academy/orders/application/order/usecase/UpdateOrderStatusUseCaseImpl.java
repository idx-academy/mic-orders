package com.academy.orders.application.order.usecase;

import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders.domain.order.repository.OrderRepository;
import com.academy.orders.domain.order.usecase.UpdateOrderStatusUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateOrderStatusUseCaseImpl implements UpdateOrderStatusUseCase {
	private final OrderRepository orderRepository;

	@Override
	public void updateOrderStatus(UUID orderId, OrderStatus orderStatus) {
		orderRepository.updateOrderStatus(orderId, orderStatus);
	}
}
