package com.academy.orders.infrastructure.product.repository;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders.infrastructure.ModelUtils;
import com.academy.orders.infrastructure.common.PageableMapper;
import com.academy.orders.infrastructure.product.ProductManagementMapper;
import com.academy.orders.infrastructure.product.ProductMapper;
import com.academy.orders.infrastructure.product.ProductPageMapper;
import com.academy.orders.infrastructure.product.ProductTranslationManagementMapper;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static com.academy.orders.infrastructure.ModelUtils.getManagementFilterDto;
import static com.academy.orders.infrastructure.ModelUtils.getPageImplOf;
import static com.academy.orders.infrastructure.ModelUtils.getPageOf;
import static com.academy.orders.infrastructure.ModelUtils.getPageable;
import static com.academy.orders.infrastructure.ModelUtils.getProduct;
import static com.academy.orders.infrastructure.ModelUtils.getProductEntity;
import static com.academy.orders.infrastructure.ModelUtils.getProductManagement;
import static com.academy.orders.infrastructure.ModelUtils.getProductTranslationEntity;
import static com.academy.orders.infrastructure.ModelUtils.getProductTranslationManagement;
import static com.academy.orders.infrastructure.TestConstants.LANGUAGE_EN;
import static com.academy.orders.infrastructure.TestConstants.TEST_UUID;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
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
	private ProductPageMapper productPageMapper;
	@Mock
	private PageableMapper pageableMapper;

	@Mock
	private ProductTranslationManagementMapper productTranslationManagementMapper;

	@Mock
	private ProductManagementMapper productManagementMapper;

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

	@ParameterizedTest
	@MethodSource("findAllProductsMethodSourceProvider")
	void findAllProductsTest(List<String> tagsParams, List<String> mockedTags) {
		var pageable = getPageable();
		var sort = String.join(",", pageable.sort());
		var productEntity = getProductEntity();
		var product = getProduct();
		var pageDomain = getPageOf(product);

		var page = new PageImpl<>(List.of(productEntity));
		when(productJpaAdapter.findAllByLanguageCodeAndStatusVisible(LANGUAGE_EN,
				PageRequest.of(pageable.page(), pageable.size()), sort, mockedTags)).thenReturn(page);
		when(productPageMapper.toDomain(page)).thenReturn(pageDomain);
		var products = productRepository.findAllProducts(LANGUAGE_EN, pageable, tagsParams);

		assertEquals(pageDomain, products);
		verify(productJpaAdapter).findAllByLanguageCodeAndStatusVisible(LANGUAGE_EN,
				PageRequest.of(pageable.page(), pageable.size()), sort, mockedTags);
		verify(productPageMapper).toDomain(page);
	}

	static Stream<Arguments> findAllProductsMethodSourceProvider() {
		return Stream.of(of(emptyList(), emptyList()), of(null, emptyList()),
				of(singletonList("category:computer"), singletonList("category:computer")));
	}

	@Test
	void updateStatusTest() {
		UUID productId = TEST_UUID;
		ProductStatus status = ProductStatus.VISIBLE;

		doNothing().when(productJpaAdapter).updateProductStatus(productId, status);
		productRepository.updateStatus(productId, status);
		verify(productJpaAdapter).updateProductStatus(productId, status);
	}

	@Test
	void findTranslationsByProductIdTest() {
		UUID productId = UUID.randomUUID();
		var productTranslationEntity = getProductTranslationEntity();
		var productTranslationManagement = getProductTranslationManagement();

		when(productJpaAdapter.findTranslationsByProductId(productId)).thenReturn(Set.of(productTranslationEntity));
		when(productTranslationManagementMapper.fromEntities(Set.of(productTranslationEntity)))
				.thenReturn(Set.of(productTranslationManagement));

		var result = productRepository.findTranslationsByProductId(productId);
		assertEquals(Set.of(productTranslationManagement), result);

		verify(productJpaAdapter).findTranslationsByProductId(productId);
		verify(productTranslationManagementMapper).fromEntities(Set.of(productTranslationEntity));
	}

	@Test
	void updateTest() {
		var productManagement = getProductManagement();
		var productEntity = getProductEntity();

		when(productManagementMapper.toEntity(productManagement)).thenReturn(productEntity);
		doAnswer(invocation -> null).when(productJpaAdapter).save(productEntity);

		productRepository.update(productManagement);

		verify(productManagementMapper).toEntity(productManagement);
		verify(productJpaAdapter).save(productEntity);
	}

	@Test
	@SuppressWarnings("unchecked")
	void findAllByLanguageWithFilterTest() {
		var filter = getManagementFilterDto();
		var pageableDomain = getPageable();
		var lang = "en";
		var sort = Sort.by(String.join(",", pageableDomain.sort()));
		var pageable = PageRequest.of(pageableDomain.page(), pageableDomain.size(), sort);
		var productEntity = getProductEntity();
		var product = getProduct();
		var ids = getPageImplOf(productEntity.getId());
		var productPage = getPageOf(product);

		when(pageableMapper.fromDomain(pageableDomain)).thenReturn(pageable);
		when(productJpaAdapter.findProductsIdsByLangAndFilters(lang, filter, pageable)).thenReturn(ids);
		when(productJpaAdapter.findProductsByIds(lang, ids.getContent(), pageable.getSort()))
				.thenReturn(singletonList(productEntity));
		when(productPageMapper.toDomain(any(PageImpl.class))).thenReturn(productPage);

		var actual = productRepository.findAllByLanguageWithFilter(lang, filter, pageableDomain);
		assertEquals(productPage, actual);

		verify(pageableMapper).fromDomain(pageableDomain);
		verify(productJpaAdapter).findProductsIdsByLangAndFilters(lang, filter, pageable);
		verify(productJpaAdapter).findProductsByIds(lang, ids.getContent(), pageable.getSort());
		verify(productPageMapper).toDomain(any(PageImpl.class));

	}

	@Test
	void saveTest() {
		var productManagement = getProductManagement();
		var productEntity = getProductEntity();
		var product = getProduct();

		when(productManagementMapper.toEntity(productManagement)).thenReturn(productEntity);
		when(productJpaAdapter.save(productEntity)).thenReturn(productEntity);
		when(productMapper.fromEntity(productEntity)).thenReturn(product);

		var result = productRepository.save(productManagement);

		assertNotNull(result);
		assertEquals(product, result);

		verify(productManagementMapper).toEntity(productManagement);
		verify(productJpaAdapter).save(productEntity);
		verify(productMapper).fromEntity(productEntity);
	}

	@Test
	void getByIdTest() {
		var product = getProduct();
		var productEntity = getProductEntity();

		when(productJpaAdapter.findById(TEST_UUID)).thenReturn(Optional.of(productEntity));
		when(productMapper.fromEntity(productEntity)).thenReturn(product);

		var result = productRepository.getById(TEST_UUID);
		assertEquals(result, Optional.of(product));

		verify(productJpaAdapter).findById(TEST_UUID);
		verify(productMapper).fromEntity(productEntity);
	}

	@Test
	void searchProductsByNameTest() {
		// Given
		var pageableDomain = getPageable();
		var pageable = PageRequest.of(0, 7);
		var entityPage = ModelUtils.getPageImplOf(getProductTranslationEntity());
		var page = ModelUtils.getPageOf(getProduct());
		String searchQuery = "some text";
		String lang = "en";

		when(pageableMapper.fromDomain(pageableDomain)).thenReturn(pageable);
		when(productJpaAdapter.findProductsByNameWithSearchQuery(searchQuery, lang, pageable)).thenReturn(entityPage);
		when(productPageMapper.fromProductTranslationEntity(entityPage)).thenReturn(page);

		// When
		Page<Product> actual = productRepository.searchProductsByName(searchQuery, lang, pageableDomain);

		// Then
		assertEquals(page, actual);
		verify(pageableMapper).fromDomain(pageableDomain);
		verify(productJpaAdapter).findProductsByNameWithSearchQuery(searchQuery, lang, pageable);
		verify(productPageMapper).fromProductTranslationEntity(entityPage);
	}
}
