package com.academy.orders.domain.order.entity.enumerated;

import com.academy.orders.domain.order.exception.OrderFinalStateException;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum OrderStatus {
	IN_PROGRESS, SHIPPED, DELIVERED, COMPLETED, CANCELED;

	private static final Map<OrderStatus, Set<OrderStatus>> allowedTransitions = new EnumMap<>(OrderStatus.class);

	static {
		allowedTransitions.put(IN_PROGRESS, EnumSet.of(IN_PROGRESS, SHIPPED, DELIVERED, COMPLETED, CANCELED));
		allowedTransitions.put(SHIPPED, EnumSet.of(SHIPPED, DELIVERED, COMPLETED, CANCELED));
		allowedTransitions.put(DELIVERED, EnumSet.of(DELIVERED, COMPLETED, CANCELED));
	}

	public boolean canTransitionTo(OrderStatus newStatus) {
		Set<OrderStatus> transitions = allowedTransitions.get(this);
		if (transitions == null) {
			throw new OrderFinalStateException();
		}
		return transitions.contains(newStatus);
	}

	public static List<String> getAllowedTransitions(OrderStatus status, boolean isAdmin) {
		if (isAdmin) {
			return getAllTransitions();
		}
		Set<OrderStatus> transitions = allowedTransitions.getOrDefault(status, EnumSet.noneOf(OrderStatus.class));
		Set<OrderStatus> result = EnumSet.copyOf(transitions);
		result.remove(status);
		return result.stream().map(OrderStatus::name).collect(Collectors.toList());
	}

	public static List<String> getAllTransitions() {
		return Stream.of(OrderStatus.values()).map(OrderStatus::name).collect(Collectors.toList());
	}
}
