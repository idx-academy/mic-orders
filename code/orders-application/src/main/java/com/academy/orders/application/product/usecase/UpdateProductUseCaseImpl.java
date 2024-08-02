package com.academy.orders.application.product.usecase;

import com.academy.orders.domain.language.repository.LanguageRepository;
import com.academy.orders.domain.language.repository.exception.LanguageNotFoundException;
import com.academy.orders.domain.product.dto.ProductRequestDto;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.ProductManagement;
import com.academy.orders.domain.product.entity.ProductTranslationManagement;
import com.academy.orders.domain.product.entity.Tag;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UpdateProductUseCaseImpl implements UpdateProductUseCase {
	private final ProductRepository productRepository;
	private final TagRepository tagRepository;
	private final LanguageRepository languageRepository;

	@Transactional
	@Override
	public void updateProduct(UUID productId, ProductRequestDto request) {
		var existingProduct = productRepository.getById(productId)
				.orElseThrow(() -> new ProductNotFoundException(productId));
		var existingTranslations = productRepository.findTranslationsByProductId(productId);

		var tags = getTags(request, existingProduct);

		request.productTranslations().forEach(dto -> languageRepository.findByCode(dto.languageCode())
				.orElseThrow(() -> new LanguageNotFoundException(dto.languageCode())));

		var existingTranslationsMap = existingTranslations.stream()
				.collect(Collectors.toMap(t -> t.language().code(), t -> t));

		var updatedTranslations = request.productTranslations().stream().map(dto -> {
			var existingTranslation = existingTranslationsMap.get(dto.languageCode());
			return new ProductTranslationManagement(existingProduct.id(), existingTranslation.language().id(),
					getValue(dto.name(), existingTranslation.name()),
					getValue(dto.description(), existingTranslation.description()), existingTranslation.language());
		}).collect(Collectors.toSet());

		var updatedProduct = new ProductManagement(existingProduct.id(),
				ProductStatus.valueOf(getValue(request.status(), String.valueOf(existingProduct.status()))),
				getValue(request.image(), existingProduct.image()), existingProduct.createdAt(),
				getValue(request.quantity(), existingProduct.quantity()),
				getValue(request.price(), existingProduct.price()), tags, updatedTranslations);

		productRepository.update(updatedProduct);
	}

	/**
	 * Determines the set of tags based on the request and the existing product. If
	 * request.tagIds() is empty, returns the existing tags from existingProduct. If
	 * request.tagIds() contains -1, returns an empty set (indicating removal of all
	 * tags). Otherwise, returns tags corresponding to request.tagIds() from the
	 * repository.
	 */
	private Set<Tag> getTags(ProductRequestDto request, Product existingProduct) {
		if (request.tagIds().isEmpty()) {
			return existingProduct.tags();
		}

		if (request.tagIds().size() == 1 && request.tagIds().get(0) == -1) {
			return Set.of();
		}
		return tagRepository.getTagsByIds(request.tagIds());
	}

	private <T> T getValue(T newValue, T existingValue) {
		return newValue != null ? newValue : existingValue;
	}
}
