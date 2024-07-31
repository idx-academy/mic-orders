package com.academy.orders.domain.order.exception;
import lombok.Getter;

@Getter
public class OrderFinalStateException extends RuntimeException {

	public OrderFinalStateException() {
		super("The order has already been completed or canceled and cannot be updated.");
	}
}
