package com.academy.orders.application.product.usecase;

import com.academy.orders.domain.common.exception.BadRequestException;
import com.academy.orders.domain.language.repository.LanguageRepository;
import com.academy.orders.domain.language.exception.LanguageNotFoundException;
import com.academy.orders.domain.product.dto.ProductRequestDto;
import com.academy.orders.domain.product.entity.Language;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.ProductManagement;
import com.academy.orders.domain.product.entity.ProductTranslationManagement;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders.domain.product.repository.ProductImageRepository;
import com.academy.orders.domain.product.repository.ProductRepository;
import com.academy.orders.domain.product.usecase.CreateProductUseCase;
import com.academy.orders.domain.product.usecase.ExtractNameFromUrlUseCase;
import com.academy.orders.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateProductUseCaseImpl implements CreateProductUseCase {
	private final ProductRepository productRepository;
	private final TagRepository tagRepository;
	private final LanguageRepository languageRepository;
	private final ProductImageRepository productImageRepository;
	private final ExtractNameFromUrlUseCase extractNameFromUrlUseCase;

	@Override
	public Product createProduct(ProductRequestDto request) {
		if (request == null) {
			throw new BadRequestException("Request cannot be null") {
			};
		}
		var imageName = extractNameFromUrlUseCase.extractNameFromUrl(request.image());
		var tags = tagRepository.getTagsByIds(request.tagIds());

		var product = ProductManagement.builder().status(ProductStatus.valueOf(request.status())).image(imageName)
				.createdAt(LocalDateTime.now()).quantity(request.quantity()).price(request.price()).tags(tags)
				.productTranslationManagement(Set.of()).build();

		var productWithoutTranslation = productRepository.save(product);

		var productTranslations = request.productTranslations().stream().map(dto -> {
			var language = languageRepository.findByCode(dto.languageCode())
					.orElseThrow(() -> new LanguageNotFoundException(dto.languageCode()));
			return new ProductTranslationManagement(productWithoutTranslation.id(), language.id(), dto.name(),
					dto.description(), new Language(language.id(), dto.languageCode()));
		}).collect(Collectors.toSet());

		var productWithTranslations = new ProductManagement(productWithoutTranslation.id(), product.status(),
				product.image(), product.createdAt(), product.quantity(), product.price(), product.tags(),
				productTranslations);

		var savedProduct = productRepository.save(productWithTranslations);
		return productImageRepository.loadImageForProduct(savedProduct);
	}
}
