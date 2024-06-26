package com.academy.orders.domain.order.entity;

import com.academy.orders.domain.order.entity.enumerated.DeliveryMethod;

public record PostAddress (
    Long id,
    DeliveryMethod deliveryMethod,
    String city,
    String department) {

}