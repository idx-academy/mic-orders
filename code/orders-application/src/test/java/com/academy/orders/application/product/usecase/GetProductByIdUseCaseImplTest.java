package com.academy.orders.application.product.usecase;

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

import static com.academy.orders.application.ModelUtils.getProductWithImageLink;
import static com.academy.orders.application.ModelUtils.getProductWithImageName;
import static com.academy.orders.application.TestConstants.TEST_UUID;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetProductByIdUseCaseImplTest {
	@InjectMocks
	private GetProductByIdUseCaseImpl getProductByIdUseCase;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private ProductImageRepository productImageRepository;

	@Test
	void getProductByIdUseCaseTest() {
		var productWithImageLink = getProductWithImageLink();
		var productWithImageName = getProductWithImageName();

		when(productRepository.getById(TEST_UUID)).thenReturn(Optional.of(productWithImageName));
		when(productImageRepository.loadImageForProduct(productWithImageName)).thenReturn(productWithImageLink);

		var result = getProductByIdUseCase.getProductById(TEST_UUID);
		Assertions.assertEquals(result, productWithImageLink);

		verify(productRepository).getById(TEST_UUID);
		verify(productImageRepository).loadImageForProduct(productWithImageName);
	}

	@Test
	void getProductByIdUseCaseThrowsNotFoundExceptionTest() {
		when(productRepository.getById(TEST_UUID)).thenReturn(Optional.empty());

		assertThrows(ProductNotFoundException.class, () -> getProductByIdUseCase.getProductById(TEST_UUID));

		verify(productRepository).getById(TEST_UUID);
	}
}
