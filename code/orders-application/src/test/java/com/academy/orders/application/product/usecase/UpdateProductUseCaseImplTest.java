package com.academy.orders.application.product.usecase;

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
import static com.academy.orders.application.ModelUtils.getProductTranslationManagement;
import static com.academy.orders.application.ModelUtils.getTag;
import static com.academy.orders.application.ModelUtils.getUpdateProduct;
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

	@Test
	void updateProductTest() {
		var updateProduct = getUpdateProduct();
		var productTranslationManagement = getProductTranslationManagement();

		when(productRepository.existById(TEST_UUID)).thenReturn(true);
		when(tagRepository.getTagsByIds(List.of(TEST_ID))).thenReturn(Set.of(getTag()));

		when(productRepository.findByIdAndLanguageCode(TEST_UUID, LANGUAGE_EN))
				.thenReturn(productTranslationManagement);
		doNothing().when(productRepository).update(any(ProductManagement.class));

		updateProductUseCase.updateProduct(TEST_UUID, LANGUAGE_EN, updateProduct);

		verify(productRepository).existById(TEST_UUID);
		verify(productRepository).findByIdAndLanguageCode(TEST_UUID, LANGUAGE_EN);
	}

	@Test
	void updateProductThrowNotFoundExceptionTest() {
		var updateProduct = getUpdateProduct();
		when(productRepository.existById(TEST_UUID)).thenReturn(false);
		assertThrows(ProductNotFoundException.class,
				() -> updateProductUseCase.updateProduct(TEST_UUID, LANGUAGE_EN, updateProduct));
		verify(productRepository).existById(TEST_UUID);
	}
}
