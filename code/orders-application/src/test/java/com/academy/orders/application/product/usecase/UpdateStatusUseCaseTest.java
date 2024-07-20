package com.academy.orders.application.product.usecase;

import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders.domain.product.exception.ProductNotFoundException;
import com.academy.orders.domain.product.repository.ProductRepository;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.academy.orders.application.TestConstants.TEST_UUID;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateStatusUseCaseTest {
	@Mock
	private ProductRepository productRepository;
	@InjectMocks
	private UpdateStatusUseCaseImpl updateStatusUseCase;

	@Test
	void testGetAllProductsTest() {
		// Given
		UUID productId = TEST_UUID;
		ProductStatus status = ProductStatus.VISIBLE;

		when(productRepository.existById(productId)).thenReturn(true);
		doNothing().when(productRepository).updateStatus(productId, status);

		// When
		updateStatusUseCase.updateStatus(productId, status);

		// Then
		verify(productRepository).existById(productId);
		verify(productRepository).updateStatus(productId, status);
	}

	@Test
	void testGetAllProductsThrowProductNotFoundExceptionTest() {
		// Given
		UUID productId = TEST_UUID;
		ProductStatus status = ProductStatus.VISIBLE;

		when(productRepository.existById(productId)).thenReturn(false);

		// Then
		assertThrows(ProductNotFoundException.class, () -> updateStatusUseCase.updateStatus(productId, status));
	}
}
