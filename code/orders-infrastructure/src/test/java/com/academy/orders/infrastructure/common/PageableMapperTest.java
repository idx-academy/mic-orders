package com.academy.orders.infrastructure.common;

import com.academy.orders.domain.common.Pageable;
import com.academy.orders.infrastructure.ModelUtils;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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

	@Test
	void mapPropertiesToSortIfOneSplitByCommaTest() {
		var properties = List.of("createdAt", "DESC");
		var expected = Sort.by(Sort.Order.desc("createdAt"));

		var actual = pageableMapper.mapPropertiesToSort(properties);

		assertEquals(expected, actual);
	}

	@Test
	void mapPropertiesToSortTest() {
		var properties = List.of("createdAt, ASC", "id", "status, DESC");
		var expected = Sort.by(Sort.Order.asc("createdAt"), Sort.Order.asc("id"), Sort.Order.desc("status"));

		var actual = pageableMapper.mapPropertiesToSort(properties);
		assertEquals(expected, actual);
	}

}
