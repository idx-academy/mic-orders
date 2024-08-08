package com.academy.orders.boot.config.rest;

import com.academy.images_api.generated.ApiClient;
import com.academy.images_api.generated.api.ImagesApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImagesApiClientConfiguration {

	@Bean("apiClient")
	public ApiClient colorsApiClient(@Value("${rest.client.colors.base-url}") String baseUrl) {
		return new ApiClient().setBasePath(baseUrl);
	}

	@Bean("imageApiClient")
	public ImagesApi imagesApi(final @Qualifier("apiClient") ApiClient apiClient) {
		return new ImagesApi(apiClient);
	}
}
