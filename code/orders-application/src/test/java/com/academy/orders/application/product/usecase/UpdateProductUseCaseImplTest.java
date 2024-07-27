package com.academy.orders.application.product.usecase;

import com.academy.orders.domain.language.repository.LanguageRepository;
import com.academy.orders.domain.language.repository.exception.LanguageNotFoundException;
import com.academy.orders.domain.product.entity.ProductManagement;
import com.academy.orders.domain.product.exception.ProductNotFoundException;
import com.academy.orders.domain.product.repository.ProductRepository;
import com.academy.orders.domain.tag.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Set;

import static com.academy.orders.application.ModelUtils.getEmptyUpdateProduct;
import static com.academy.orders.application.ModelUtils.getLanguage;
import static com.academy.orders.application.ModelUtils.getProductManagement;
import static com.academy.orders.application.ModelUtils.getProductTranslationManagement;
import static com.academy.orders.application.ModelUtils.getTag;
import static com.academy.orders.application.ModelUtils.getUpdateProduct;
import static com.academy.orders.application.TestConstants.*;
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

	@Test
	void updateProductTest() {
		var updateProduct = getUpdateProduct();
		var productManagement = getProductManagement();
		var productTranslationManagement = getProductTranslationManagement();
		var language = getLanguage();

		when(productRepository.existById(TEST_UUID)).thenReturn(true);
		when(languageRepository.findByCode(LANGUAGE_EN)).thenReturn(language);
		when(tagRepository.getTagsByIds(List.of(TEST_ID))).thenReturn(Set.of(getTag()));
		when(productRepository.findTranslationByIdAndLanguageCode(TEST_UUID, LANGUAGE_EN))
				.thenReturn(productTranslationManagement);
		when(productRepository.findProductByIdAndLanguageCode(TEST_UUID, LANGUAGE_EN)).thenReturn(productManagement);

		doNothing().when(productRepository).update(any(ProductManagement.class));

		updateProductUseCase.updateProduct(TEST_UUID, LANGUAGE_EN, updateProduct);

		verify(productRepository).existById(TEST_UUID);
		verify(languageRepository).findByCode(LANGUAGE_EN);
		verify(tagRepository).getTagsByIds(List.of(TEST_ID));
		verify(productRepository).findTranslationByIdAndLanguageCode(TEST_UUID, LANGUAGE_EN);
		verify(productRepository).findProductByIdAndLanguageCode(TEST_UUID, LANGUAGE_EN);
	}

	@Test
	void updateProductWithEmptyRequestTest() {
		var updateProduct = getEmptyUpdateProduct();
		var productManagement = getProductManagement();
		var productTranslationManagement = getProductTranslationManagement();
		var language = getLanguage();

		when(productRepository.existById(TEST_UUID)).thenReturn(true);
		when(languageRepository.findByCode(LANGUAGE_EN)).thenReturn(language);
		when(tagRepository.getTagsByIds(List.of(TEST_ID))).thenReturn(Set.of(getTag()));
		when(productRepository.findTranslationByIdAndLanguageCode(TEST_UUID, LANGUAGE_EN))
				.thenReturn(productTranslationManagement);
		when(productRepository.findProductByIdAndLanguageCode(TEST_UUID, LANGUAGE_EN)).thenReturn(productManagement);

		doNothing().when(productRepository).update(any(ProductManagement.class));

		updateProductUseCase.updateProduct(TEST_UUID, LANGUAGE_EN, updateProduct);

		verify(productRepository).existById(TEST_UUID);
		verify(languageRepository).findByCode(LANGUAGE_EN);
		verify(tagRepository).getTagsByIds(List.of(TEST_ID));
		verify(productRepository).findTranslationByIdAndLanguageCode(TEST_UUID, LANGUAGE_EN);
		verify(productRepository).findProductByIdAndLanguageCode(TEST_UUID, LANGUAGE_EN);
	}

	@Test
	void updateProductThrowsProductNotFoundExceptionTest() {
		var updateProduct = getUpdateProduct();
		when(productRepository.existById(TEST_UUID)).thenReturn(false);
		assertThrows(ProductNotFoundException.class,
				() -> updateProductUseCase.updateProduct(TEST_UUID, LANGUAGE_EN, updateProduct));
		verify(productRepository).existById(TEST_UUID);
	}

	@Test
	void updateProductThrowsLanguageNotFoundExceptionTest() {
		var updateProduct = getUpdateProduct();
		when(productRepository.existById(TEST_UUID)).thenReturn(true);
		when(languageRepository.findByCode(LANGUAGE_EN)).thenReturn(null);

		assertThrows(LanguageNotFoundException.class,
				() -> updateProductUseCase.updateProduct(TEST_UUID, LANGUAGE_EN, updateProduct));

		verify(productRepository).existById(TEST_UUID);
		verify(languageRepository).findByCode(LANGUAGE_EN);
	}
}
