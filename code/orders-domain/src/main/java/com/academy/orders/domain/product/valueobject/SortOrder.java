package com.academy.orders.domain.product.valueobject;

import java.util.Arrays;
import java.util.List;

public record SortOrder(String property, Direction direction) {

	public enum Direction {
		ASC, DESC
	}

	public static List<SortOrder> fromString(String sort) {
		if (sort == null || sort.isEmpty()) {
			return List.of();
		}
		return Arrays.stream(sort.split(",")).map(s -> {
			String[] parts = s.split(":");
			String property = parts[0];
			Direction direction = parts.length > 1 && "desc".equalsIgnoreCase(parts[1])
					? Direction.DESC
					: Direction.ASC;
			return new SortOrder(property, direction);
		}).toList();
	}
}
