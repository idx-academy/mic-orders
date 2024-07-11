package com.academy.orders.boot.apirest.orders.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.academy.orders.boot.apirest.orders.common.AbstractControllerIT;
import com.academy.orders_api_rest.generated.model.UserOrderDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

class OrdersControllerIT extends AbstractControllerIT {
	@Value("${auth.users[1].username}")
	private String username;

	@Test
	void testGetOrderById() {
		// Given
		final var url = baseUrl() + "/v1/orders/8efbee82-8a0c-407a-a4c0-16bbad40a23e";
		final HttpHeaders headers = buildAuthHeaders(username);
		final var requestEntity = new HttpEntity<>(headers);

		// When
		final var result = this.restTemplate.exchange(url, HttpMethod.GET, requestEntity, UserOrderDTO.class);

		// Then
		assertNotNull(result);
		assertEquals(200, result.getStatusCode().value());
		// TODO check schema
	}

}