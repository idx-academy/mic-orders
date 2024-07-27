package com.academy.orders.domain.order.entity.enumerated;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public enum OrderStatus {
	IN_PROGRESS, SHIPPED, DELIVERED, CANCELED, COMPLETED;

	private static final Map<OrderStatus, Set<OrderStatus>> allowedTransitions = new EnumMap<>(OrderStatus.class);

	static {
		allowedTransitions.put(IN_PROGRESS, EnumSet.of(SHIPPED, DELIVERED, COMPLETED, CANCELED));
		allowedTransitions.put(SHIPPED, EnumSet.of(DELIVERED, COMPLETED, CANCELED));
		allowedTransitions.put(DELIVERED, EnumSet.of(COMPLETED, CANCELED));
		allowedTransitions.put(CANCELED, EnumSet.noneOf(OrderStatus.class));
		allowedTransitions.put(COMPLETED, EnumSet.noneOf(OrderStatus.class));
	}

	public boolean canTransitionTo(OrderStatus newStatus) {
		return allowedTransitions.get(this).contains(newStatus);
	}
}
