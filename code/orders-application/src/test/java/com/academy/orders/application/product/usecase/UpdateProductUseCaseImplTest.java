package com.academy.orders.application.product.usecase;

import com.academy.orders.domain.language.repository.LanguageRepository;
import com.academy.orders.domain.language.repository.exception.LanguageNotFoundException;
import com.academy.orders.domain.product.dto.ProductRequestDto;
import com.academy.orders.domain.product.entity.ProductManagement;
import com.academy.orders.domain.product.exception.ProductNotFoundException;
import com.academy.orders.domain.product.repository.ProductRepository;
import com.academy.orders.domain.tag.repository.TagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static com.academy.orders.application.ModelUtils.getEmptyProductRequestDto;
import static com.academy.orders.application.ModelUtils.getLanguage;
import static com.academy.orders.application.ModelUtils.getProduct;
import static com.academy.orders.application.ModelUtils.getProductRequestDto;
import static com.academy.orders.application.ModelUtils.getProductRequestRemoveAllTagsDto;
import static com.academy.orders.application.ModelUtils.getProductRequestWithDifferentIds;
import static com.academy.orders.application.ModelUtils.getProductRequestWithEmptyTagsDto;
import static com.academy.orders.application.ModelUtils.getProductTranslationManagement;
import static com.academy.orders.application.ModelUtils.getTag;
import static com.academy.orders.application.TestConstants.LANGUAGE_EN;
import static com.academy.orders.application.TestConstants.TEST_ID;
import static com.academy.orders.application.TestConstants.TEST_UUID;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateProductUseCaseImplTest {
	@InjectMocks
	private UpdateProductUseCaseImpl updateProductUseCase;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private TagRepository tagRepository;

	@Mock
	private LanguageRepository languageRepository;

	@ParameterizedTest
	@MethodSource("provideProductRequestsWithTags")
	void updateProductWithTagsTest(ProductRequestDto request, List<Long> tagIds) {
		var product = getProduct();
		var productTranslationManagement = getProductTranslationManagement();
		var language = getLanguage();

		when(productRepository.getById(TEST_UUID)).thenReturn(Optional.ofNullable(product));
		when(productRepository.findTranslationsByProductId(TEST_UUID)).thenReturn(Set.of(productTranslationManagement));
		when(tagRepository.getTagsByIds(tagIds)).thenReturn(Set.of(getTag()));
		when(languageRepository.findByCode(LANGUAGE_EN)).thenReturn(Optional.ofNullable(language));

		doNothing().when(productRepository).update(any(ProductManagement.class));

		updateProductUseCase.updateProduct(TEST_UUID, request);

		verify(productRepository).getById(TEST_UUID);
		verify(productRepository).findTranslationsByProductId(TEST_UUID);
		verify(tagRepository).getTagsByIds(tagIds);
		verify(languageRepository).findByCode(LANGUAGE_EN);

		var productCaptor = ArgumentCaptor.forClass(ProductManagement.class);
		verify(productRepository).update(productCaptor.capture());
		var updatedProduct = productCaptor.getValue();

		var updatedTranslations = updatedProduct.productTranslationManagement();

		updatedTranslations.forEach(
				updatedTranslation -> Assertions.assertEquals(LANGUAGE_EN, updatedTranslation.language().code()));
	}

	@ParameterizedTest
	@MethodSource("provideProductRequestsWithoutTags")
	void updateProductWithoutTagsTest(ProductRequestDto request) {
		var product = getProduct();
		var productTranslationManagement = getProductTranslationManagement();
		var language = getLanguage();

		when(productRepository.getById(TEST_UUID)).thenReturn(Optional.ofNullable(product));
		when(productRepository.findTranslationsByProductId(TEST_UUID)).thenReturn(Set.of(productTranslationManagement));
		when(languageRepository.findByCode(LANGUAGE_EN)).thenReturn(Optional.ofNullable(language));

		doNothing().when(productRepository).update(any(ProductManagement.class));

		updateProductUseCase.updateProduct(TEST_UUID, request);

		verify(productRepository).getById(TEST_UUID);
		verify(productRepository).findTranslationsByProductId(TEST_UUID);
		verify(languageRepository).findByCode(LANGUAGE_EN);
	}

	@Test
	void updateProductThrowsProductNotFoundExceptionTest() {
		var request = getProductRequestDto();
		when(productRepository.getById(TEST_UUID)).thenReturn(Optional.empty());
		assertThrows(ProductNotFoundException.class, () -> updateProductUseCase.updateProduct(TEST_UUID, request));

		verify(productRepository).getById(TEST_UUID);
	}

	@Test
	void updateProductThrowsLanguageNotFoundExceptionTest() {
		var request = getProductRequestDto();
		var product = getProduct();
		var productTranslationManagement = getProductTranslationManagement();

		when(productRepository.getById(TEST_UUID)).thenReturn(Optional.ofNullable(product));
		when(productRepository.findTranslationsByProductId(TEST_UUID)).thenReturn(Set.of(productTranslationManagement));
		when(tagRepository.getTagsByIds(List.of(TEST_ID))).thenReturn(Set.of(getTag()));
		when(languageRepository.findByCode(LANGUAGE_EN)).thenReturn(Optional.empty());

		assertThrows(LanguageNotFoundException.class, () -> updateProductUseCase.updateProduct(TEST_UUID, request));

		verify(productRepository).getById(TEST_UUID);
		verify(productRepository).findTranslationsByProductId(TEST_UUID);
		verify(tagRepository).getTagsByIds(List.of(TEST_ID));
	}

	private static Stream<Arguments> provideProductRequestsWithTags() {
		return Stream.of(Arguments.of(getProductRequestDto(), List.of(TEST_ID)),
				Arguments.of(getEmptyProductRequestDto(), List.of(TEST_ID)),
				Arguments.of(getProductRequestWithDifferentIds(), List.of(-1L, TEST_ID)));
	}

	private static Stream<Arguments> provideProductRequestsWithoutTags() {
		return Stream.of(Arguments.of(getProductRequestWithEmptyTagsDto()),
				Arguments.of(getProductRequestRemoveAllTagsDto()));
	}
}
