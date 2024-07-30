package com.academy.orders.infrastructure.common.repository;

import com.academy.colors_api.generated.api.ImagesApi;
import com.academy.orders.domain.common.respository.ImageRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class ImageRepositoryImpl implements ImageRepository {
	private final ImagesApi imagesApi;

	@Value("${images.default.url}")
	private String defaultImageUrl;

	@CircuitBreaker(name = "imagesCircuit", fallbackMethod = "getDefaultUrl")
	@CachePut(value = "images", key = "#name")
	public String getImageLinkByName(String name) {
		log.info("Call images api with with name param: {}", name);
		return imagesApi.getImageByName(name).getImageUrl();
	}

	@Cacheable(value = "images", key = "#name")
	public String getDefaultUrl(String name, Throwable exception) {
		log.warn("Call api with name {} failed {}", name, exception.getMessage());
		return defaultImageUrl;
	}
}