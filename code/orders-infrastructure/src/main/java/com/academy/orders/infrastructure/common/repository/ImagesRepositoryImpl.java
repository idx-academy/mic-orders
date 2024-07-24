package com.academy.orders.infrastructure.common.repository;

import com.academy.colors_api.generated.api.ImagesApi;
import com.academy.orders.infrastructure.account.entity.AccountEntity;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Component
@Slf4j
public class ImagesRepositoryImpl {
	private final ImagesApi imagesApi;
	private final CacheManager cacheManager;

	// @Retry(name = "imagesRetry")
	@CircuitBreaker(name = "imagesCircuit", fallbackMethod = "getDefaultUrl")
	@CachePut(value = "images", key = "#name")
	public String getImageLinkByName(String name) {
		log.info("Call api with name {}", name);
		return imagesApi.getImageByName(name).getImageUrl();
	}


	//OR @Cacheable(name = "images", key = "#name")
	public String getDefaultUrl(String name, Throwable exception) {
		var defaultUrl = "https://localhost:8080/default.png";
		log.warn("Call api with name {} failed {}", name, exception.getMessage());
		var cache = cacheManager.getCache("images");
		if (nonNull(cache)) {
			cache.get(name, ()-> {
				log.warn("No cache for it {}", name);
				return defaultUrl;
			});
		}
		return defaultUrl;
	}

}
