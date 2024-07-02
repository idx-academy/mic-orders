package com.academy.orders.infrastructure.product.repository;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryImplTest {
	@InjectMocks
	private ProductRepositoryImpl productRepository;
	@Mock
	private ProductJpaAdapter productJpaAdapter;

	@ParameterizedTest
	@CsvSource({"true", "false"})
	void testExistsById(Boolean response) {

		when(productJpaAdapter.existsById(any(UUID.class))).thenReturn(response);

		assertEquals(response, productRepository.existById(UUID.randomUUID()));
		verify(productJpaAdapter).existsById(any(UUID.class));
	}

	@Test
	void testSetNewProductQuantity() {
		doNothing().when(productJpaAdapter).setNewProductQuantity(any(UUID.class), anyInt());
		assertDoesNotThrow(() -> productRepository.setNewProductQuantity(UUID.randomUUID(), 1));
	}
}
