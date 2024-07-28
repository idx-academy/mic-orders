package com.academy.orders.application.product.usecase;

import com.academy.orders.application.ModelUtils;
import com.academy.orders.domain.exception.NotFoundException;
import com.academy.orders.domain.language.repository.LanguageRepository;
import com.academy.orders.domain.language.repository.exception.LanguageNotFoundException;
import com.academy.orders.domain.product.dto.CreateProductRequestDto;
import com.academy.orders.domain.product.dto.ProductTranslationDto;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.ProductManagement;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders.domain.product.repository.ProductRepository;
import com.academy.orders.domain.tag.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateProductUseCaseImplTest {
	@InjectMocks
	private CreateProductUseCaseImpl createProductUseCase;
	@Mock
	private ProductRepository productRepository;
	@Mock
	private TagRepository tagRepository;
	@Mock
	private LanguageRepository languageRepository;

	@Test
	void createProductTest() {
		UUID productId = UUID.randomUUID();
		CreateProductRequestDto request = ModelUtils.getCreateProductRequestDto();

		Product product = Product.builder().id(productId).status(ProductStatus.valueOf(request.status()))
				.image(request.image()).createdAt(LocalDateTime.now()).quantity(request.quantity())
				.price(request.price()).tags(Set.of(ModelUtils.getTag()))
				.productTranslations(Set.of(ModelUtils.getProductTranslation())).build();

		when(tagRepository.getTagsByIds(request.tagIds())).thenReturn(Set.of(ModelUtils.getTag()));
		when(languageRepository.findByCode(request.productTranslations().iterator().next().languageCode()))
				.thenReturn(ModelUtils.getLanguageEn());
		when(productRepository.save(any(ProductManagement.class))).thenReturn(product);

		Product result = createProductUseCase.createProduct(request);

		assertNotNull(result);
		assertEquals(productId, result.id());
		assertEquals(request.status(), result.status().name());
		assertEquals(request.image(), result.image());
		assertEquals(request.quantity(), result.quantity());
		assertEquals(request.price(), result.price());
		assertEquals(1, result.tags().size());
		assertEquals(1, result.productTranslations().size());
	}

	@Test
	void createProductNullRequestTest() {
		assertThrows(NotFoundException.class, () -> createProductUseCase.createProduct(null));
	}

	@Test
	void createProductProductRepositorySaveReturnsNullTest() {
		CreateProductRequestDto request = ModelUtils.getCreateProductRequestDto();

		when(productRepository.save(any(ProductManagement.class))).thenReturn(null);

		assertThrows(RuntimeException.class, () -> createProductUseCase.createProduct(request));
	}

	@Test
	void createProductLanguageNotFoundTest() {
		CreateProductRequestDto request = CreateProductRequestDto.builder().status("VISIBLE").image("image.jpg")
				.quantity(10).price(BigDecimal.valueOf(100)).tagIds(List.of(1L))
				.productTranslations(Set.of(ProductTranslationDto.builder().name("Name").description("Description")
						.languageCode("invalid").build()))
				.build();

		when(tagRepository.getTagsByIds(request.tagIds())).thenReturn(Set.of(ModelUtils.getTag()));
		when(languageRepository.findByCode("invalid")).thenReturn(null);

		assertThrows(LanguageNotFoundException.class, () -> createProductUseCase.createProduct(request));
	}
}
