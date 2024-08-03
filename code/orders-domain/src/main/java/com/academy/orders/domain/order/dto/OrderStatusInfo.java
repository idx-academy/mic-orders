package com.academy.orders.domain.order.dto;

import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import lombok.Builder;
import java.util.List;

@Builder
public record OrderStatusInfo(List<OrderStatus> availableStatuses, Boolean isPaid) {
}
