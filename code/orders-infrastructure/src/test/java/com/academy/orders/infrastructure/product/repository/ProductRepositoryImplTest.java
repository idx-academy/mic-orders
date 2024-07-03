package com.academy.orders.infrastructure.product.repository;

import java.util.List;
import java.util.UUID;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.infrastructure.ModelUtils;
import com.academy.orders.infrastructure.product.ProductMapper;
import com.academy.orders.infrastructure.product.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

	@Mock
	private List<ProductEntity> productEntities;

	@Mock
	private List<Product> products;

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

	@Test
	void testGetAllProducts() {
		String language = "en";
		Pageable pageable = ModelUtils.getPageable();
		String sort = "price,desc";

		org.springframework.data.domain.Page<ProductEntity> productEntityPage = new PageImpl<>(productEntities);
		when(productJpaAdapter.findAllByLanguageCode(anyString(), any(PageRequest.class), anyString()))
				.thenReturn(productEntityPage);
		when(productMapper.fromEntities(anyList())).thenReturn(products);

		Page<Product> result = productRepository.getAllProducts(language, pageable);

		verify(productJpaAdapter).findAllByLanguageCode(eq(language), any(PageRequest.class), eq(sort));
		verify(productMapper).fromEntities(productEntities);

		assertEquals(productEntityPage.getTotalElements(), result.totalElements());
		assertEquals(productEntityPage.getTotalPages(), result.totalPages());
		assertEquals(productEntityPage.isFirst(), result.first());
		assertEquals(productEntityPage.isLast(), result.last());
		assertEquals(productEntityPage.getNumber(), result.number());
		assertEquals(productEntityPage.getNumberOfElements(), result.numberOfElements());
		assertEquals(productEntityPage.getSize(), result.size());
		assertEquals(productEntityPage.isEmpty(), result.empty());
		assertEquals(products, result.content());
	}
}
