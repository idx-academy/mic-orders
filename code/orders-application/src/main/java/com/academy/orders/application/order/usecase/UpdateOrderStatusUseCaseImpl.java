package com.academy.orders.application.order.usecase;

import com.academy.orders.domain.account.exception.AccountRoleNotFoundException;
import com.academy.orders.domain.account.repository.AccountRepository;
import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders.domain.order.exception.InvalidOrderStatusTransitionException;
import com.academy.orders.domain.order.exception.OrderNotFoundException;
import com.academy.orders.domain.order.repository.OrderRepository;
import com.academy.orders.domain.order.usecase.UpdateOrderStatusUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateOrderStatusUseCaseImpl implements UpdateOrderStatusUseCase {
	private final OrderRepository orderRepository;
	private final AccountRepository accountRepository;
	private static final String ROLE_ADMIN = "role_admin";

	@Override
	public void updateOrderStatus(UUID orderId, OrderStatus orderStatus, String currentAccountEmail) {
		var order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
		var role = accountRepository.findRoleByEmail(currentAccountEmail)
				.orElseThrow(() -> new AccountRoleNotFoundException(currentAccountEmail));

		if (ROLE_ADMIN.equalsIgnoreCase(String.valueOf(role))) {
			orderRepository.updateOrderStatus(order.id(), orderStatus);
		} else if (order.orderStatus().canTransitionTo(orderStatus)) {
			orderRepository.updateOrderStatus(order.id(), orderStatus);
		} else {
			throw new InvalidOrderStatusTransitionException(order.orderStatus(), orderStatus);
		}
	}
}
