package com.academy.orders.domain.order.exception;

import com.academy.orders.domain.common.exception.PaidException;
import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderAlreadyPaidException extends PaidException {
	private final UUID orderId;

	public OrderAlreadyPaidException(UUID orderId) {
		super(String.format("Order with ID: %s is already paid.", orderId));
		this.orderId = orderId;
	}
}
