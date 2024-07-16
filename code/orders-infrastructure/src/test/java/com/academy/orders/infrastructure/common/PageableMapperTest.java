package com.academy.orders.infrastructure.common;

import com.academy.orders.domain.common.Pageable;
import com.academy.orders.infrastructure.ModelUtils;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class PageableMapperTest {
	@InjectMocks
	private final PageableMapper pageableMapper = new PageableMapper() {
	};

	private final Pageable pageable = ModelUtils.getPageable();

	@Test
	void fromDomainWithNullPageableTest() {
		PageRequest result = pageableMapper.fromDomain(null);
		assertNull(result);
	}

	@Test
	void fromDomainWithValidPageableTest() {
		Sort sort = Sort.by(pageable.sort().toArray(new String[0]));

		PageRequest result = pageableMapper.fromDomain(pageable);

		assertNotNull(result);
		assertEquals(pageable.page(), result.getPageNumber());
		assertEquals(pageable.size(), result.getPageSize());
		assertEquals(sort, result.getSort());
	}

	@Test
	void mapWithValidListTest() {
		Sort expected = Sort.by(
				List.of(Sort.Order.asc("pr1"), Sort.Order.asc("pr2"), Sort.Order.desc("pr3"), Sort.Order.asc("pr4")));
		List<String> sort = List.of("pr1", "pr2", "asc", "pr3", "desc", "pr4");

		Sort actual = pageableMapper.map(sort);

		assertEquals(expected, actual);
	}

	@Test
	void mapWithEmptyListTest() {
		Sort expected = Sort.unsorted();
		List<String> sort = List.of();

		Sort actual = pageableMapper.map(sort);

		assertEquals(expected, actual);
	}

	@Test
	void mapWithValidSortTest() {
		Sort sort = Sort.by(Sort.Direction.ASC, "value");
		List<String> expected = sort.get().map(o -> o.getProperty() + " " + o.getDirection()).toList();

		List<String> actual = pageableMapper.map(sort);

		assertEquals(expected, actual);
	}
}
