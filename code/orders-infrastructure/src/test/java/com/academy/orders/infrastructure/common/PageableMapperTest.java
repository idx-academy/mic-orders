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
		List<String> sort = pageable.sort();
		Sort result = pageableMapper.map(sort);

		assertNotNull(result);
		assertNotNull(result.getOrderFor(sort.get(0)));
	}

	@Test
	void mapWithEmptyListTest() {
		List<String> sortProperties = List.of();
		Sort result = pageableMapper.map(sortProperties);

		assertNotNull(result);
		assertEquals(Sort.unsorted(), result);
	}
}
