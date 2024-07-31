package com.academy.orders.application.product.usecase;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.repository.ProductImageRepository;
import com.academy.orders.domain.product.repository.ProductRepository;
import com.academy.orders.domain.product.usecase.GetAllProductsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAllProductsUseCaseImpl implements GetAllProductsUseCase {
	private final ProductRepository productRepository;
	private final ProductImageRepository productImageRepository;

	@Override
	public Page<Product> getAllProducts(String language, Pageable pageable) {
		return productRepository.getAllProducts(language, pageable).map(productImageRepository::loadImageForProduct);
	}
}
