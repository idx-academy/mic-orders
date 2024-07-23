package com.academy.orders.domain.product.usecase;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.product.dto.ProductManagementFilterDto;
import com.academy.orders.domain.product.entity.Product;

public interface GetManagerProductsUseCase {
	Page<Product> getManagerProducts(Pageable pageable, ProductManagementFilterDto productManagementFilter,
			String lang);
}
