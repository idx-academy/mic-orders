package com.academy.orders.apirest.common.mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class LocalDateTimeMapperTest {
	private final LocalDateTimeMapper localDateTimeMapper = new LocalDateTimeMapper() {
	};

	@Test
	public void testMap() {
		// Given
		LocalDateTime localDateTime = LocalDateTime.of(2023, 7, 3, 12, 0);

		// When
		OffsetDateTime offsetDateTime = localDateTimeMapper.map(localDateTime);

		// Then
		OffsetDateTime expectedOffsetDateTime = OffsetDateTime.of(localDateTime, ZoneOffset.UTC);
		assertEquals(expectedOffsetDateTime, offsetDateTime);
	}
}
