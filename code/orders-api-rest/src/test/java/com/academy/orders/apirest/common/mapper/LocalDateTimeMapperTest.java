package com.academy.orders.apirest.common.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class LocalDateTimeMapperTest {
	private final LocalDateTimeMapper localDateTimeMapper = new LocalDateTimeMapper() {
	};

	@Test
	void mapLocalDateTimeToOffsetDateTimeTest() {
		// Given
		LocalDateTime localDateTime = LocalDateTime.of(2023, 7, 3, 12, 0);
		OffsetDateTime expectedOffsetDateTime = OffsetDateTime.of(localDateTime, ZoneOffset.UTC);

		// When
		OffsetDateTime offsetDateTime = localDateTimeMapper.map(localDateTime);

		// Then
		assertEquals(expectedOffsetDateTime, offsetDateTime);
	}

	@Test
	void mapOffsetDateTimeToLocalDateTimeTest() {
		// Given
		OffsetDateTime offsetDateTime = OffsetDateTime.of(2023, 7, 3, 12, 1, 1, 1, ZoneOffset.UTC);
		LocalDateTime expected = offsetDateTime.toLocalDateTime();

		// When
		LocalDateTime localDateTime = localDateTimeMapper.map(offsetDateTime);

		// Then
		assertEquals(expected, localDateTime);
	}

	@Test
	void mapOffsetDateTimeToLocalDateTimeIfNullTest() {
		// When
		LocalDateTime localDateTime = localDateTimeMapper.map((OffsetDateTime) null);

		// Then
		assertNull(localDateTime);
	}
}
