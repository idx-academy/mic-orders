package com.academy.orders.infrastructure.common;

import com.academy.orders.domain.common.Pageable;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static java.util.Objects.isNull;

@Mapper(componentModel = "spring")
public interface PageableMapper {
	default PageRequest fromDomain(Pageable pageable) {
		if (pageable == null) {
			return null;
		}
		Sort sort = mapPropertiesToSort(pageable.sort());
		int pageNumber = pageable.page();
		int pageSize = pageable.size();

		return PageRequest.of(pageNumber, pageSize, sort);
	}

	default Sort mapPropertiesToSort(List<String> values) {
		if (isPropertiesIncorrect(values)) {
			return Sort.unsorted();
		}
		if (isOnePropertySplitByComma(values)) {
			return mapSplitByCommaValueToSort(values);
		}

		return values.stream().filter(value -> !value.isBlank()).map(this::mapToSort)
				.collect(Collectors.collectingAndThen(Collectors.toList(), Sort::by));
	}

	private static boolean isPropertiesIncorrect(List<String> values) {
		return isNull(values) || values.isEmpty() || values.stream().allMatch(String::isBlank);
	}

	private boolean isOnePropertySplitByComma(List<String> values) {
		return values.size() == 2 && isDirectionString(values);
	}

	private boolean isDirectionString(List<String> values) {
		return values.get(1).trim().equalsIgnoreCase("ASC") || values.get(1).trim().equalsIgnoreCase("DESC");
	}

	private Sort mapSplitByCommaValueToSort(List<String> values) {
		var property = values.get(0).trim();
		var direction = values.get(1).trim();
		return Sort.by(Sort.Direction.fromString(direction), property);
	}

	private Sort.Order mapToSort(String sortValue) {
		var sortElements = sortValue.split(",");
		var property = sortElements[0].trim();
		var direction = sortElements.length > 1 ? sortElements[1].trim().toUpperCase() : "ASC";
		return new Sort.Order(Sort.Direction.fromString(direction), property);
	}
}
