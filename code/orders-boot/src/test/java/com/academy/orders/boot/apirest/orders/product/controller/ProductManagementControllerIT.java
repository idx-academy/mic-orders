package com.academy.orders.boot.apirest.orders.product.controller;

import com.academy.orders.boot.apirest.orders.common.AbstractControllerIT;
import com.academy.orders_api_rest.generated.model.ProductManagementPageDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProductManagementControllerIT extends AbstractControllerIT {
	@Value("${auth.users[2].username}")
	private String username;

	@Test
	void getProductForManagerTest() {
		final var url = baseUrl() + "/v1/management/products?lang=uk&page=0&size=10&sort=createdAt, DESC";
		final HttpHeaders headers = buildAuthHeaders(username);
		final var requestEntity = new HttpEntity<>(headers);

		final var result = this.restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				ProductManagementPageDTO.class);
		final var productPage = result.getBody();

		assertEquals(200, result.getStatusCode().value());
		assertNotNull(result);
		assertNotNull(productPage);
		assertNotNull(productPage.getContent());
		assertFalse(productPage.getContent().isEmpty());
		System.out.println(productPage.getContent().get(0).getCreatedAt());
	}
}
