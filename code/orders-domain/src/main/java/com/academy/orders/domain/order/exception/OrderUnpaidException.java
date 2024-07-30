package com.academy.orders.domain.order.exception;

import com.academy.orders.domain.exception.PaidException;
import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderUnpaidException extends PaidException {
	private final UUID orderId;
	private final String status;

	public OrderUnpaidException(UUID orderId, String status) {
		super(String.format("Order with ID: %s must be paid before changing status to " + status, orderId));
		this.orderId = orderId;
		this.status = status;
	}
}
