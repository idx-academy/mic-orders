package com.academy.orders.domain.order.exception;
import com.academy.orders.domain.common.exception.BadRequestException;
import lombok.Getter;

@Getter
public class OrderFinalStateException extends BadRequestException {

	public OrderFinalStateException() {
		super("The order has already been completed or canceled and cannot be updated.");
	}
}
