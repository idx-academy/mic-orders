package com.academy.orders.infrastructure.product.repository;

import java.util.List;
import java.util.UUID;
import com.academy.orders.infrastructure.product.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import static com.academy.orders.infrastructure.ModelUtils.getPageable;
import static com.academy.orders.infrastructure.ModelUtils.getProduct;
import static com.academy.orders.infrastructure.ModelUtils.getProductEntity;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryImplTest {
	@InjectMocks
	private ProductRepositoryImpl productRepository;
	@Mock
	private ProductJpaAdapter productJpaAdapter;

	@Mock
	private ProductMapper productMapper;

	@ParameterizedTest
	@CsvSource({"true", "false"})
	void existsByIdTest(Boolean response) {

		when(productJpaAdapter.existsById(any(UUID.class))).thenReturn(response);

		assertEquals(response, productRepository.existById(UUID.randomUUID()));
		verify(productJpaAdapter).existsById(any(UUID.class));
	}

	@Test
	void setNewProductQuantityTest() {
		doNothing().when(productJpaAdapter).setNewProductQuantity(any(UUID.class), anyInt());
		assertDoesNotThrow(() -> productRepository.setNewProductQuantity(UUID.randomUUID(), 1));
	}

	@Test
	void getAllProductsTest() {
		var pageable = getPageable();
		String sort = String.join(",", pageable.sort());
		var productEntity = getProductEntity();
		var product = getProduct();
		var page = new PageImpl<>(List.of(productEntity));
		when(productJpaAdapter.findAllByLanguageCode("en", PageRequest.of(pageable.page(), pageable.size()), sort))
				.thenReturn(page);

		when(productMapper.fromEntities(page.getContent())).thenReturn(List.of(product));
		var products = productRepository.getAllProducts("en", pageable);
		assertEquals(1, products.size());

		verify(productJpaAdapter).findAllByLanguageCode("en", PageRequest.of(pageable.page(), pageable.size()), sort);
		verify(productMapper).fromEntities(page.getContent());
	}
}
