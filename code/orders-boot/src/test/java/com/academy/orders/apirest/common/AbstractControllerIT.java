package com.academy.orders.apirest.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.academy.orders.apirest.constant.TestConstants;
import com.academy.orders_api_rest.generated.model.SignInRequestDTO;
import java.util.HashMap;
import java.util.Map;
import com.academy.orders.boot.Application;
import com.academy.orders_api_rest.generated.model.AuthTokenResponseDTO;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = {Application.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("it")
public abstract class AbstractControllerIT {

	@Value(value = "${local.server.port}")
	private int port;

	@Value(value = "${server.servlet.context-path}")
	private String contextPath;

	@Autowired
	protected TestRestTemplate restTemplate;

	private final Map<String, String> userToken = new HashMap<>();

	protected HttpHeaders buildAdminAuthHeaders() {
		return buildAuthHeaders(TestConstants.ADMIN_MAIL, TestConstants.ADMIN_PASSWORD);
	}

	protected HttpHeaders buildUserAuthHeaders() {
		return buildAuthHeaders(TestConstants.USER_MAIL, TestConstants.USER_PASSWORD);
	}

	private HttpHeaders buildAuthHeaders(String email, String password) {
		final var headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + getAuthToken(email, password));
		return headers;
	}

	private String getAuthToken(String username, String password) {
		var token = userToken.get(username);
		if (token == null) {
			var body = new HttpEntity<>(new SignInRequestDTO(username, password));
			var authResponse = restTemplate.exchange(baseUrl() + "/auth/sign-in", HttpMethod.POST, body,
					AuthTokenResponseDTO.class);
			assertEquals(200, authResponse.getStatusCode().value());

			token = authResponse.getBody().getToken();
			userToken.put(username, token);
		}
		return token;
	}

	protected String baseUrl() {
		return String.format("http://localhost:%d%s", port, contextPath);
	}
}
