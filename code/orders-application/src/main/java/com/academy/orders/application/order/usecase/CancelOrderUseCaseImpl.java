package com.academy.orders.application.order.usecase;

import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders.domain.order.exception.OrderFinalStateException;
import com.academy.orders.domain.order.exception.OrderNotFoundException;
import com.academy.orders.domain.order.repository.OrderRepository;
import com.academy.orders.domain.order.usecase.CancelOrderUseCase;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CancelOrderUseCaseImpl implements CancelOrderUseCase {
	private final OrderRepository orderRepository;

	@Override
	public void cancelOrder(Long userId, UUID orderId) {
		Order order = orderRepository.findByIdFetchData(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
		if (order.orderStatus().equals(OrderStatus.CANCELED) || order.orderStatus().equals(OrderStatus.COMPLETED)) {
			throw new OrderFinalStateException();
		}

		orderRepository.updateOrderStatus(orderId, OrderStatus.CANCELED);
	}
}
