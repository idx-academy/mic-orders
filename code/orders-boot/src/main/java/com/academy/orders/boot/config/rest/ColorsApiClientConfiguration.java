package com.academy.orders.boot.config.rest;

import com.academy.colors_api.generated.ApiClient;
import com.academy.colors_api.generated.api.ColorsApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ColorsApiClientConfiguration {

	@Bean("colorsApiClient")
	public ApiClient colorsApiClient(@Value("${rest.client.colors.base-url}") String baseUrl) {
		return new ApiClient().setBasePath(baseUrl);
	}

	@Bean
	public ColorsApi colorsApi(@Qualifier("colorsApiClient") final ApiClient apiClient) {
		return new ColorsApi(apiClient);
	}
}
