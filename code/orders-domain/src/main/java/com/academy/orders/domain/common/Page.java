package com.academy.orders.domain.common;

import java.util.List;

public record Page<T> (Long totalElements, Integer totalPages, Boolean first, Boolean last, Integer number,
		Integer numberOfElements, Integer size, Boolean empty, List<T> content) {
}
