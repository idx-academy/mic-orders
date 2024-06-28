package com.academy.orders.domain.order.entity;

import com.academy.orders.domain.order.entity.enumerated.DeliveryMethod;
import lombok.Builder;

@Builder
public record PostAddress(DeliveryMethod deliveryMethod, String city, String department) {

}