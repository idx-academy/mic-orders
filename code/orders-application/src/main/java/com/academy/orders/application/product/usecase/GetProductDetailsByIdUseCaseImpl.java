package com.academy.orders.application.product.usecase;

import com.academy.orders.domain.language.repository.LanguageRepository;
import com.academy.orders.domain.language.repository.exception.LanguageNotFoundException;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.exception.ProductNotFoundException;
import com.academy.orders.domain.product.repository.ProductImageRepository;
import com.academy.orders.domain.product.repository.ProductRepository;
import com.academy.orders.domain.product.usecase.GetProductDetailsByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetProductDetailsByIdUseCaseImpl implements GetProductDetailsByIdUseCase {
	private final ProductRepository productRepository;
	private final LanguageRepository languageRepository;
	private final ProductImageRepository productImageRepository;

	@Override
	public Product getProductDetailsById(UUID productId, String lang) {
		var language = languageRepository.findByCode(lang).orElseThrow(() -> new LanguageNotFoundException(lang));
		return productRepository.getByIdAndLanguageCode(productId, language.code())
				.map(productImageRepository::loadImageForProduct)
				.orElseThrow(() -> new ProductNotFoundException(productId));
	}
}
