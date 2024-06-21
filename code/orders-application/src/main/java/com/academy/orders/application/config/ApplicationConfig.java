package com.academy.orders.application.config;

import com.academy.orders.domain.product.repository.ProductRepository;
import com.academy.orders.domain.product.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

	@Bean
	public ProductService productService(ProductRepository productRepository) {
		return new ProductService(productRepository);
	}
}
