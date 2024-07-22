package com.academy.orders.domain.product.dto;

import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import java.time.LocalDateTime;
import java.util.List;

public record ProductManagementFilterDto(ProductStatus status, String searchByName, Double priceMore, Double priceLess,
		Integer quantityMore, Integer quantityLess, LocalDateTime createdAfter, LocalDateTime createdBefore,
		List<String> tags) {
}