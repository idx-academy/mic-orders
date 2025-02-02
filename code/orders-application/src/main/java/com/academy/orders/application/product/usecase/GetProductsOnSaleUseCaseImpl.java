package com.academy.orders.application.product.usecase;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.usecase.GetProductsOnSaleUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.academy.orders.domain.product.repository.ProductImageRepository;
import com.academy.orders.domain.product.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class GetProductsOnSaleUseCaseImpl implements GetProductsOnSaleUseCase {
	private final ProductRepository productRepository;
	private final ProductImageRepository productImageRepository;

	@Override
	public Page<Product> getProductsOnSale(Pageable pageable, String lang) {
		var products = productRepository.findProductsWhereDiscountIsNotNull(lang, pageable);
		return products.map(Product::applyDiscount).map(productImageRepository::loadImageForProduct);
	}
}
