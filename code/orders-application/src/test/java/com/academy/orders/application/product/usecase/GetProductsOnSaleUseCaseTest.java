package com.academy.orders.application.product.usecase;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.repository.ProductImageRepository;
import com.academy.orders.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.academy.orders.application.ModelUtils.*;
import static com.academy.orders.application.TestConstants.LANGUAGE_EN;
import static com.academy.orders.application.TestConstants.TEST_DISCOUNT_PRICE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetProductsOnSaleUseCaseTest {
	@InjectMocks
	private GetProductsOnSaleUseCaseImpl getProductsOnSaleUseCase;
	@Mock
	private ProductRepository productRepository;
	@Mock
	private ProductImageRepository productImageRepository;

	@Test
	void getProductsOnSaleTest() {
		var lang = LANGUAGE_EN;
		var size = 5;
		var pageable = Pageable.builder().page(0).size(size).build();
		var product = getProductWithImageUrlAndDiscount();
		var expectedProduct = getProductWithImageUrlAndAppliedDiscount();
		var page = getPage(List.of(product), 1L, 1, 0, size);

		when(productRepository.findProductsWhereDiscountIsNotNull(lang, pageable)).thenReturn(page);
		when(productImageRepository.loadImageForProduct(any(Product.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));

		assertNull(product.getPriceWithDiscount());

		var actual = getProductsOnSaleUseCase.getProductsOnSale(pageable, lang);

		assertNotNull(actual);
		assertEquals(1, actual.content().size());
		assertEquals(expectedProduct, actual.content().get(0));
		assertNotNull(actual.content().get(0).getPriceWithDiscount());

		verify(productRepository).findProductsWhereDiscountIsNotNull(lang, pageable);
		verify(productImageRepository).loadImageForProduct(product);
	}

	@Test
	void getProductsOnSaleReturnsEmptyPageTest() {
		var lang = LANGUAGE_EN;
		var size = 5;
		var pageable = Pageable.builder().page(0).size(size).build();

		var page = getPage(new ArrayList<Product>(), 0L, 0, 0, size);

		when(productRepository.findProductsWhereDiscountIsNotNull(lang, pageable)).thenReturn(page);

		var actual = getProductsOnSaleUseCase.getProductsOnSale(pageable, lang);

		assertNotNull(actual);
		assertEquals(0, actual.content().size());

		verify(productRepository).findProductsWhereDiscountIsNotNull(lang, pageable);
		verifyNoInteractions(productImageRepository);
	}
}
