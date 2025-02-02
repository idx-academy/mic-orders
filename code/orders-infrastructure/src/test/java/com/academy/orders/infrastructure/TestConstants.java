package com.academy.orders.infrastructure;

import java.time.LocalDateTime;
import java.util.UUID;

public class TestConstants {
	public static final UUID TEST_UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
	public static final Long TEST_ID = 1L;
	public static final String LANGUAGE_EN = "en";
	public static final String TEST_EMAIL = "manager@mail.com";
	public static final int TEST_AMOUNT = 10;
	public static final LocalDateTime TEST_START_DATE = LocalDateTime.of(2020, 1, 1, 15, 4);
	public static final LocalDateTime TEST_END_DATE = LocalDateTime.of(2020, 2, 1, 15, 4);
}
