package com.academy.orders.domain.order.dto;

import lombok.Builder;
import java.util.List;

@Builder
public record OrderStatusInfo(List<String> availableStatuses, Boolean isPaid) {
}
