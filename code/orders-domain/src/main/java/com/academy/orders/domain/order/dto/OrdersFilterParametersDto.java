package com.academy.orders.domain.order.dto;

import com.academy.orders.domain.order.entity.enumerated.DeliveryMethod;
import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record OrdersFilterParametersDto(String accountEmail, List<DeliveryMethod> deliveryMethods,
		List<OrderStatus> statuses, Boolean isPaid, LocalDateTime createdBefore, LocalDateTime createdAfter,
		BigDecimal totalMore, BigDecimal totalLess) {
}
