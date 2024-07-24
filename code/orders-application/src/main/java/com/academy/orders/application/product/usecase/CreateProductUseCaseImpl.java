package com.academy.orders.application.product.usecase;

import com.academy.orders.domain.language.repository.LanguageRepository;
import com.academy.orders.domain.product.dto.CreateProductRequestDto;
import com.academy.orders.domain.product.entity.*;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders.domain.product.repository.ProductRepository;
import com.academy.orders.domain.product.usecase.CreateProductUseCase;
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

	@Override
	public Product createProduct(CreateProductRequestDto request) {
		var tags = tagRepository.getTagsByIds(request.tagIds());

		var product = ProductManagement.builder().status(ProductStatus.valueOf(request.status())).image(request.image())
				.createdAt(LocalDateTime.now()).quantity(request.quantity()).price(request.price()).tags(tags)
				.productTranslationManagement(Set.of()).build();

		var productWithoutTranslation = productRepository.save(product);

		var productTranslations = request.productTranslations().stream()
				.map(dto -> new ProductTranslationManagement(productWithoutTranslation.id(),
						languageRepository.findByCode(dto.languageCode()).id(), dto.name(), dto.description(),
						new Language(languageRepository.findByCode(dto.languageCode()).id(), dto.languageCode())))
				.collect(Collectors.toSet());

		var productWithTranslations = new ProductManagement(productWithoutTranslation.id(), product.status(),
				product.image(), product.createdAt(), product.quantity(), product.price(), product.tags(),
				productTranslations);

		return productRepository.save(productWithTranslations);
	}
}
