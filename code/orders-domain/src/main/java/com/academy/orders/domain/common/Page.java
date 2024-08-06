package com.academy.orders.domain.common;

import java.util.List;
import java.util.function.Function;
import lombok.Builder;

@Builder
public record Page<T> (Long totalElements, Integer totalPages, Boolean first, Boolean last, Integer number,
		Integer numberOfElements, Integer size, Boolean empty, List<T> content) {

	public <D> Page<D> map(Function<T, D> mapper) {
		List<D> list = null;
		if (content != null) {
			list = content.stream().map(mapper).toList();
		}
		return Page.<D>builder().totalElements(totalElements).totalPages(totalPages).first(first).last(last)
				.number(number).numberOfElements(numberOfElements).size(size).empty(empty).content(list).build();

	}
}
