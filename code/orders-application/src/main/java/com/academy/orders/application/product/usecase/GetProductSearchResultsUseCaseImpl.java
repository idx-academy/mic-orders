package com.academy.orders.application.product.usecase;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.repository.ProductImageRepository;
import com.academy.orders.domain.product.repository.ProductRepository;
import com.academy.orders.domain.product.usecase.GetProductSearchResultsUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetProductSearchResultsUseCaseImpl implements GetProductSearchResultsUseCase {
	private final ProductRepository productRepository;
	private final ProductImageRepository productImageRepository;

	@Override
	public Page<Product> findProductsBySearchQuery(String searchQuery, String lang, Pageable pageable) {
		if (pageable.sort().isEmpty()) {
			pageable = new Pageable(pageable.page(), pageable.size(), List.of("name,desc"));
		}
		return productRepository.searchProductsByName(searchQuery, lang, pageable)
				.map(productImageRepository::loadImageForProduct);
	}
}
