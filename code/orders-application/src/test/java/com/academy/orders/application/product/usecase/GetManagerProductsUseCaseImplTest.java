package com.academy.orders.application.product.usecase;

import com.academy.orders.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.academy.orders.application.ModelUtils.getManagementFilterDto;
import static com.academy.orders.application.ModelUtils.getPageOf;
import static com.academy.orders.application.ModelUtils.getPageable;
import static com.academy.orders.application.ModelUtils.getProduct;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetManagerProductsUseCaseImplTest {
	@InjectMocks
	private GetManagerProductsUseCaseImpl getManagerProductsUseCase;
	@Mock
	private ProductRepository productRepository;

	@Test
	void getManagerProductsTest() {
		var product = getProduct();
		var filter = getManagementFilterDto();
		var lang = "ua";
		var pageable = getPageable();
		var page = getPageOf(product);

		when(productRepository.findAllByLanguageWithFilter(lang, filter, pageable)).thenReturn(page);

		var actual = getManagerProductsUseCase.getManagerProducts(pageable, filter, lang);

		assertEquals(page, actual);
		verify(productRepository).findAllByLanguageWithFilter(lang, filter, pageable);
	}
}