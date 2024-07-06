package com.academy.orders.infrastructure.common;

import com.academy.orders.domain.common.Pageable;
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

	default Sort map(List<String> value) {
		return Sort.by(value.toArray(new String[0]));
	}
}
