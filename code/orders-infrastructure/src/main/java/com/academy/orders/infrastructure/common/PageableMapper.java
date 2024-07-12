package com.academy.orders.infrastructure.common;

import com.academy.orders.domain.common.Pageable;
import java.util.LinkedList;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Mapper(componentModel = "spring")
public interface PageableMapper {
	default PageRequest fromDomain(Pageable pageable) {
		if (pageable == null) {
			return null;
		}

		Sort sort = null;

		sort = map(pageable.sort());

		int pageNumber = pageable.page();
		int pageSize = pageable.size();

		return PageRequest.of(pageNumber, pageSize, sort);
	}

	default List<String> map(Sort value) {
		return value.stream().map(o -> o.getProperty() + " " + o.getDirection()).toList();
	}

	default Sort map(List<String> value) {
		List<Sort.Order> orderList = new LinkedList<>();
		if(value.isEmpty()) {
			return Sort.unsorted();
		}

		for (int i = 0; i < value.size() - 1; i++) {
			String param = value.get(i);
			String nextValue = value.get(i + 1);
			Sort.Direction direction = Sort.Direction.ASC;

			if (nextValue.equalsIgnoreCase("desc")) {
				direction = Sort.Direction.DESC;
			} else if (!nextValue.equalsIgnoreCase("asc")) {
				orderList.add(Sort.Order.asc(param));
				continue;
			}
			orderList.add(new Sort.Order(direction, param));
			i++;
		}

		String last = value.get(value.size() - 1);
		if (!last.equalsIgnoreCase("asc") && !last.equalsIgnoreCase("desc")) {
			orderList.add(Sort.Order.asc(last));
		}
		return Sort.by(orderList);
	}
}
