package com.academy.orders.apirest.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import com.academy.orders.boot.Application;
import com.academy.orders.boot.config.UsersConfig;
import com.academy.orders.boot.config.UsersConfig.AppUser;
import com.academy.orders_api_rest.generated.model.AuthTokenRequestDTO;
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
	private UsersConfig usersConfig;

	@Autowired
	protected TestRestTemplate restTemplate;

	private final Map<String, String> userToken = new HashMap<>();

	protected HttpHeaders buildAuthHeaders(String username) {
		final var headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + getAuthToken(username));
		return headers;
	}

	private String getAuthToken(String username) {
		var token = userToken.get(username);
		if (token == null) {
			var password = usersConfig.users().stream().filter(usr -> usr.username().equals(username))
					.map(AppUser::password).findFirst()
					.orElseThrow(() -> new RuntimeException(username + " not found"));

			var body = new HttpEntity<>(new AuthTokenRequestDTO(username, password));
			var authResponse = restTemplate.exchange("/auth/login", HttpMethod.POST, body, AuthTokenResponseDTO.class);
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
