package com.academy.orders.domain.order.dto;

import com.academy.orders.domain.order.entity.enumerated.DeliveryMethod;
import lombok.Builder;

@Builder
public record CreateOrderDto(String firstName, String lastName, String email, DeliveryMethod deliveryMethod,
		String city, String department) {
}
