package com.academy.orders.application.product.usecase;

import com.academy.orders.domain.product.entity.ProductManagement;
import com.academy.orders.domain.product.entity.ProductTranslationManagement;
import com.academy.orders.domain.product.entity.UpdateProduct;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders.domain.product.exception.ProductNotFoundException;
import com.academy.orders.domain.product.repository.ProductRepository;
import com.academy.orders.domain.product.usecase.UpdateProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateProductUseCaseImpl implements UpdateProductUseCase {
	private final ProductRepository productRepository;

	@Transactional
	@Override
	public void updateProduct(UUID productId, String lang, UpdateProduct updateProduct) {
		if (!productRepository.existById(productId)) {
			throw new ProductNotFoundException(productId);
		}

		var existingProductTranslation = productRepository.findByIdAndLanguageCode(productId, lang);

		var updatedProductTranslation = new ProductTranslationManagement(existingProductTranslation.productId(),
				existingProductTranslation.languageId(), updateProduct.name(), updateProduct.description(),
				existingProductTranslation.language());

		var product = new ProductManagement(productId, ProductStatus.valueOf(updateProduct.status().toUpperCase()),
				updateProduct.image(), LocalDateTime.now(), updateProduct.quantity(), updateProduct.price(),
				updateProduct.tags(), Set.of(updatedProductTranslation));
		productRepository.update(product);
	}
}
