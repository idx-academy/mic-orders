package com.academy.orders.domain.order.exception;

import com.academy.orders.domain.common.exception.BadRequestException;
import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import lombok.Getter;

@Getter
public class InvalidOrderStatusTransitionException extends BadRequestException {
	private final OrderStatus currentStatus;
	private final OrderStatus newStatus;

	public InvalidOrderStatusTransitionException(OrderStatus currentStatus, OrderStatus newStatus) {
		super("Invalid status transition from " + currentStatus.toString() + " to " + newStatus.toString());
		this.currentStatus = currentStatus;
		this.newStatus = newStatus;
	}
}
