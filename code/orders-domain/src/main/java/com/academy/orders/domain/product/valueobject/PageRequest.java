package com.academy.orders.domain.product.valueobject;

import java.util.List;

public record PageRequest(int page, int size, List<SortOrder> sortOrders) {
	public PageRequest(int page, int size, SortOrder sortOrder) {
		this(page, size, List.of(sortOrder));
	}
}
