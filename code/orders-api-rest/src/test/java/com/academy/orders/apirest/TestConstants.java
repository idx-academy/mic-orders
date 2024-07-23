package com.academy.orders.apirest;

import java.math.BigDecimal;
import java.util.UUID;

public class TestConstants {
	public static final String GET_ALL_PRODUCTS_URL = "/v1/products";
	public static final String UPDATE_ORDER_STATUS = "/v1/orders/{orderId}/status";
	public static final String UPDATE_PRODUCT = "/v1/management/products/{productId}";
	public static final String LANGUAGE_UA = "ua";
	public static final String LANGUAGE_EN = "en";
	public static final String IMAGE_URL = "https://example.com/image.jpg";
	public static final Long TEST_ID = 1L;
	public static final UUID TEST_UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
	public static final int TEST_QUANTITY = 10;
	public static final BigDecimal TEST_PRICE = BigDecimal.valueOf(999.99);
	public static final Float TEST_FLOAT_PRICE = 999.99f;
	public static final String TAG_NAME = "electronics";
	public static final String PRODUCT_NAME = "IPhone";
	public static final String PRODUCT_DESCRIPTION = "Phone";
	public static final String TEST_FIRST_NAME = "mockedFirstName";
	public static final String TEST_LAST_NAME = "mockedLastName";
	public static final String TEST_EMAIL = "mockedmail@mail.com";
	public static final String TEST_CITY = "mockedCity";
	public static final String TEST_DEPARTMENT = "mockedDepartment";

	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_MANAGER = "ROLE_MANAGER";

}
