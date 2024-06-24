package com.academy.orders.application.product.usecase;

import com.academy.orders.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.academy.orders.application.ModelUtils.getProduct;
import static com.academy.orders.application.TestConstants.LANGUAGE_UA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllProductsUseCaseTest {
	@Mock
	private ProductRepository productRepository;
	@InjectMocks
	private GetAllProductsUseCaseImpl getAllProductsUseCase;

	@Test
	void testGetAllProducts() {
		var expected = List.of(getProduct());

		when(productRepository.getAllProducts(LANGUAGE_UA)).thenReturn(List.of(getProduct()));
		var actual = getAllProductsUseCase.getAllProducts(LANGUAGE_UA);
		Assertions.assertEquals(expected, actual);

		verify(productRepository).getAllProducts(LANGUAGE_UA);
	}
}
