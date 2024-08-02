package com.academy.orders.application.order.usecase;

import com.academy.orders.domain.account.entity.enumerated.Role;
import com.academy.orders.domain.order.dto.OrderStatusInfo;
import com.academy.orders.domain.order.dto.UpdateOrderStatusDto;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders.domain.order.exception.InvalidOrderStatusTransitionException;
import com.academy.orders.domain.order.exception.OrderAlreadyPaidException;
import com.academy.orders.domain.order.exception.OrderNotFoundException;
import com.academy.orders.domain.order.exception.OrderUnpaidException;
import com.academy.orders.domain.order.repository.OrderRepository;
import com.academy.orders.domain.order.usecase.UpdateOrderStatusUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateOrderStatusUseCaseImpl implements UpdateOrderStatusUseCase {
	private final OrderRepository orderRepository;
	private static final String ROLE_ADMIN = "role_admin";

	@Override
	public OrderStatusInfo updateOrderStatus(UUID orderId, UpdateOrderStatusDto updateOrderStatus, String role) {
		var currentOrder = getOrderById(orderId);
		boolean isAdmin = isAdminRole(Role.valueOf(role));

		if (!isAdmin) {
			validateOrderStatusUpdate(currentOrder, updateOrderStatus);
		}

		updateOrderStatuses(orderId, updateOrderStatus);
		var availableStatuses = getAvailableStatuses(isAdmin, updateOrderStatus);
		return buildOrderStatusInfo(currentOrder, updateOrderStatus, availableStatuses);
	}

	private Order getOrderById(UUID orderId) {
		return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
	}

	private boolean isAdminRole(Role accountRole) {
		return ROLE_ADMIN.equalsIgnoreCase(String.valueOf(accountRole));
	}

	private void validateOrderStatusUpdate(Order currentOrder, UpdateOrderStatusDto updateOrderStatus) {
		if (updateOrderStatus.isPaid() != null && currentOrder.isPaid()) {
			throw new OrderAlreadyPaidException(currentOrder.id());
		}

		if (!currentOrder.isPaid() && updateOrderStatus.status().equals(OrderStatus.COMPLETED)) {
			throw new OrderUnpaidException(currentOrder.id(), updateOrderStatus.status().toString());
		}

		if (!currentOrder.orderStatus().canTransitionTo(updateOrderStatus.status())) {
			throw new InvalidOrderStatusTransitionException(currentOrder.orderStatus(), updateOrderStatus.status());
		}
	}

	private void updateOrderStatuses(UUID orderId, UpdateOrderStatusDto updateOrderStatus) {
		orderRepository.updateOrderStatus(orderId, updateOrderStatus.status());

		if (updateOrderStatus.isPaid() != null) {
			orderRepository.updateIsPaidStatus(orderId, updateOrderStatus.isPaid());
		}
	}

	private List<String> getAvailableStatuses(boolean isAdmin, UpdateOrderStatusDto updateOrderStatus) {
		return isAdmin
				? OrderStatus.getAllTransitions()
				: OrderStatus.getAllowedTransitions(updateOrderStatus.status(), false);
	}

	private OrderStatusInfo buildOrderStatusInfo(Order currentOrder, UpdateOrderStatusDto updateOrderStatus,
			List<String> availableStatuses) {
		return OrderStatusInfo.builder().availableStatuses(availableStatuses)
				.isPaid(updateOrderStatus.isPaid() != null ? updateOrderStatus.isPaid() : currentOrder.isPaid())
				.build();
	}
}
