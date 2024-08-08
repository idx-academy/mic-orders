package com.academy.orders.application.product.usecase;

import com.academy.orders.domain.language.repository.LanguageRepository;
import com.academy.orders.domain.language.exception.LanguageNotFoundException;
import com.academy.orders.domain.product.exception.ProductNotFoundException;
import com.academy.orders.domain.product.repository.ProductImageRepository;
import com.academy.orders.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.academy.orders.application.ModelUtils.getLanguage;
import static com.academy.orders.application.ModelUtils.getProductWithImageLink;
import static com.academy.orders.application.ModelUtils.getProductWithImageName;
import static com.academy.orders.application.TestConstants.LANGUAGE_EN;
import static com.academy.orders.application.TestConstants.LANGUAGE_UK;
import static com.academy.orders.application.TestConstants.TEST_UUID;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetProductDetailsByIdUseCaseTest {
	@InjectMocks
	private GetProductDetailsByIdUseCaseImpl getProductDetailsByIdUseCase;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private LanguageRepository languageRepository;

	@Mock
	private ProductImageRepository productImageRepository;

	@Test
	void getProductDetailsByIdTest() {
		var productWithImageName = getProductWithImageName();
		var productWithImageLink = getProductWithImageLink();
		var language = getLanguage();

		when(languageRepository.findByCode(LANGUAGE_UK)).thenReturn(Optional.of(language));
		when(productRepository.getByIdAndLanguageCode(TEST_UUID, LANGUAGE_UK))
				.thenReturn(Optional.of(productWithImageName));
		when(productImageRepository.loadImageForProduct(productWithImageName)).thenReturn(productWithImageLink);

		var result = getProductDetailsByIdUseCase.getProductDetailsById(TEST_UUID, LANGUAGE_UK);
		Assertions.assertEquals(result, productWithImageLink);

		verify(languageRepository).findByCode(LANGUAGE_UK);
		verify(productRepository).getByIdAndLanguageCode(TEST_UUID, LANGUAGE_UK);
		verify(productImageRepository).loadImageForProduct(productWithImageName);
	}

	@Test
	void getProductDetailsByIdThrowsLanguageNotFoundTest() {
		when(languageRepository.findByCode(LANGUAGE_EN)).thenReturn(Optional.empty());

		assertThrows(LanguageNotFoundException.class,
				() -> getProductDetailsByIdUseCase.getProductDetailsById(TEST_UUID, LANGUAGE_EN));

		verify(languageRepository).findByCode(LANGUAGE_EN);
	}

	@Test
	void getProductDetailsByIdThrowsProductNotFoundTest() {
		var language = getLanguage();

		when(languageRepository.findByCode(LANGUAGE_UK)).thenReturn(Optional.of(language));
		when(productRepository.getByIdAndLanguageCode(TEST_UUID, LANGUAGE_UK)).thenReturn(Optional.empty());

		assertThrows(ProductNotFoundException.class,
				() -> getProductDetailsByIdUseCase.getProductDetailsById(TEST_UUID, LANGUAGE_UK));

		verify(languageRepository).findByCode(LANGUAGE_UK);
		verify(productRepository).getByIdAndLanguageCode(TEST_UUID, LANGUAGE_UK);
	}
}
