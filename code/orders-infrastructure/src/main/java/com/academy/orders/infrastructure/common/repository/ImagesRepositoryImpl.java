package com.academy.orders.infrastructure.common.repository;

import com.academy.colors_api.generated.api.ImagesApi;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Component
@Slf4j
public class ImagesRepositoryImpl {
	private final ImagesApi imagesApi;
	private final CacheManager cacheManager;

	@CircuitBreaker(name = "imagesCircuit", fallbackMethod = "getDefaultUrl")
	@CachePut(value = "images", key = "#name")
	public String getImageLinkByName(String name) {
		log.info("Call api with name {}", name);
		return imagesApi.getImageByName(name).getImageUrl();
	}

	//@Cacheable(name = "images", key = "#name")
	public String getDefaultUrl(String name, Throwable exception) {
		var defaultUrl = "https://localhost:8080/default.png";
		log.warn("Call api with name {} failed {}", name, exception.getMessage());

		var cache = cacheManager.getCache("images");
		if (nonNull(cache)) {
			return cache.get(name, () -> defaultUrl);
		}
		return defaultUrl;
	}

}
