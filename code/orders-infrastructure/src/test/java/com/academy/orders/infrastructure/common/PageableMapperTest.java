package com.academy.orders.infrastructure.common;

import com.academy.orders.domain.common.Pageable;
import com.academy.orders.infrastructure.ModelUtils;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.data.domain.Sort.Order.asc;

@ExtendWith(MockitoExtension.class)
class PageableMapperTest {
	private final PageableMapper pageableMapper = new PageableMapper() {
	};
	private final Pageable pageable = ModelUtils.getPageable();

	@ParameterizedTest
	@NullSource
	void fromDomainWithNullPageableTest(Pageable pageable) {
		var result = pageableMapper.fromDomain(pageable);
		assertNull(result);
	}

	@Test
	void fromDomainWithValidPageableTest() {
		var sort = Sort.by(pageable.sort().toArray(new String[0]));
		var result = pageableMapper.fromDomain(pageable);

		assertNotNull(result);
		assertEquals(pageable.page(), result.getPageNumber());
		assertEquals(pageable.size(), result.getPageSize());
		assertEquals(sort, result.getSort());
	}

	@ParameterizedTest
	@MethodSource("incorrectParametersProvider")
	void mapPropertiesToSortIncorrectDataTest(List<String> properties) {
		var actual = pageableMapper.mapPropertiesToSort(properties);
		assertEquals(Sort.unsorted(), actual);
	}

	static Stream<List<String>> incorrectParametersProvider() {
		return Stream.of(Collections.emptyList(), null, List.of("", " "));
	}

	@ParameterizedTest
	@MethodSource("sortIfOneSplitByCommaProvider")
	void mapPropertiesToSortIfOneSplitByCommaTest(List<String> properties, Sort expected) {
		var actual = pageableMapper.mapPropertiesToSort(properties);
		assertEquals(expected, actual);
	}

	static Stream<Arguments> sortIfOneSplitByCommaProvider() {
		return Stream.of(arguments(List.of("createdAt", "DESC"), Sort.by(Order.desc("createdAt"))),
				arguments(List.of("createdAt", "id"), Sort.by(asc("createdAt"), asc("id"))));
	}

	@Test
	void mapPropertiesToSortTest() {
		var properties = List.of("createdAt, ASC", " ", "status, DESC");
		var expected = Sort.by(asc("createdAt"), Order.desc("status"));

		var actual = pageableMapper.mapPropertiesToSort(properties);
		assertEquals(expected, actual);
	}

}
