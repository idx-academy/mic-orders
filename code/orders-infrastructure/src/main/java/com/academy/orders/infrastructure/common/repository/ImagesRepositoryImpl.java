package com.academy.orders.infrastructure.common.repository;

import com.academy.colors_api.generated.api.ImagesApi;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class ImagesRepositoryImpl {
	private final ImagesApi imagesApi;

	// @Retry(name = "imagesRetry")
	@CircuitBreaker(name = "imagesCircuit", fallbackMethod = "fallback")
	public String getImageLinkByName(String name) {
		log.info("Call api with name {}", name);
		return imagesApi.getImageByName(name).getImageUrl();
	}

	public String fallback(String name, Throwable exception) {
		log.warn("Call api with name {} failed {}", name, exception.getMessage());
		return "https://localhost:8080/default.png";
	}
}
