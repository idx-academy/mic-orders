package com.academy.orders.application.product.usecase;

import com.academy.orders.domain.language.repository.LanguageRepository;
import com.academy.orders.domain.language.repository.exception.LanguageNotFoundException;
import com.academy.orders.domain.product.entity.ProductManagement;
import com.academy.orders.domain.product.entity.ProductTranslationManagement;
import com.academy.orders.domain.product.dto.UpdateProductDto;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders.domain.product.exception.ProductNotFoundException;
import com.academy.orders.domain.product.repository.ProductRepository;
import com.academy.orders.domain.product.usecase.UpdateProductUseCase;
import com.academy.orders.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateProductUseCaseImpl implements UpdateProductUseCase {
	private final ProductRepository productRepository;
	private final TagRepository tagRepository;
	private final LanguageRepository languageRepository;

	@Transactional
	@Override
	public void updateProduct(UUID productId, String lang, UpdateProductDto request) {
		if (!productRepository.existById(productId)) {
			throw new ProductNotFoundException(productId);
		}
		var language = languageRepository.findByCode(lang);
		if (language == null) {
			throw new LanguageNotFoundException(lang);
		}

		var tags = tagRepository.getTagsByIds(request.tagIds());
		var translation = productRepository.findTranslationByIdAndLanguageCode(productId, lang);
		var product = productRepository.findProductByIdAndLanguageCode(productId, lang);

		var updatedTranslation = new ProductTranslationManagement(translation.productId(), translation.languageId(),
				getValue(request.name(), translation.name()),
				getValue(request.description(), translation.description()), translation.language());

		var updatedProduct = new ProductManagement(productId,
				ProductStatus.valueOf(getValue(request.status(), String.valueOf(product.status()))),
				getValue(request.image(), product.image()), product.createdAt(),
				getValue(request.quantity(), product.quantity()), getValue(request.price(), product.price()), tags,
				Set.of(updatedTranslation));

		productRepository.update(updatedProduct);
	}

	private <T> T getValue(T newValue, T existingValue) {
		return newValue != null ? newValue : existingValue;
	}
}
