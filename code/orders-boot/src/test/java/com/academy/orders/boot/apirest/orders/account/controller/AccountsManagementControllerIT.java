package com.academy.orders.boot.apirest.orders.account.controller;

import com.academy.orders.boot.apirest.orders.common.AbstractControllerIT;
import com.academy.orders.domain.account.entity.enumerated.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountsManagementControllerIT extends AbstractControllerIT {
	@Value("${auth.users[1].username}")
	private String username;

	@Test
	void updateAccountStatusTest() {
		final var accountId = 2L;
		final var status = UserStatus.ACTIVE;
		final var url = baseUrl() + format("/v1/management/users/%d/status?status=%s", accountId, status.name());
		final HttpHeaders headers = buildAuthHeaders(username);
		final var requestEntity = new HttpEntity<>(headers);

		final var result = this.restTemplate.exchange(url, HttpMethod.PATCH, requestEntity, Object.class);

		assertEquals(204, result.getStatusCode().value());
	}
}
