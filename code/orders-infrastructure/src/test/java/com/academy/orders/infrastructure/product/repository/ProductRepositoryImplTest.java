package com.academy.orders.infrastructure.product.repository;

import com.academy.orders.domain.common.respository.ImageRepository;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders.infrastructure.common.PageableMapper;
import com.academy.orders.infrastructure.product.ProductManagementMapper;
import com.academy.orders.infrastructure.product.ProductMapper;
import com.academy.orders.infrastructure.product.ProductPageMapper;
import com.academy.orders.infrastructure.product.ProductTranslationManagementMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static com.academy.orders.infrastructure.ModelUtils.TEST_IMAGE_LINK;
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
import static java.util.Collections.singletonList;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
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
	private ImageRepository imageRepository;

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

	@Test
	void getAllProductsTest() {
		var lang = "en";
		var pageable = getPageable();
		var sort = String.join(",", pageable.sort());
		var productEntity = getProductEntity();
		var product = getProduct();
		var imageName = productEntity.getImage();

		var page = new PageImpl<>(List.of(productEntity));
		when(productJpaAdapter.findAllByLanguageCodeAndStatusVisible(lang,
				PageRequest.of(pageable.page(), pageable.size()), sort)).thenReturn(page);
		when(imageRepository.getImageLinkByName(imageName)).thenReturn(TEST_IMAGE_LINK);

		when(productMapper.fromEntities(page.getContent())).thenReturn(List.of(product));
		var products = productRepository.getAllProducts(lang, pageable);
		assertEquals(1, products.size());

		verify(productJpaAdapter).findAllByLanguageCodeAndStatusVisible(lang,
				PageRequest.of(pageable.page(), pageable.size()), sort);
		verify(productMapper).fromEntities(page.getContent());
		verify(imageRepository).getImageLinkByName(imageName);
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
	void findByIdAndLanguageCodeTest() {
		UUID productId = UUID.randomUUID();
		var productTranslationEntity = getProductTranslationEntity();
		var image = productTranslationEntity.getProduct().getImage();
		var productTranslationManagement = getProductTranslationManagement();

		when(productJpaAdapter.findByIdAndLanguageCode(productId, LANGUAGE_EN)).thenReturn(productTranslationEntity);
		when(productTranslationManagementMapper.fromEntity(productTranslationEntity))
				.thenReturn(productTranslationManagement);
		when(imageRepository.getImageLinkByName(image)).thenReturn(TEST_IMAGE_LINK);

		var result = productRepository.findByIdAndLanguageCode(productId, LANGUAGE_EN);
		assertEquals(productTranslationManagement, result);

		verify(productJpaAdapter).findByIdAndLanguageCode(productId, LANGUAGE_EN);
		verify(productTranslationManagementMapper).fromEntity(productTranslationEntity);
		verify(imageRepository).getImageLinkByName(anyString());
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
		var imageName = productEntity.getImage();

		when(pageableMapper.fromDomain(pageableDomain)).thenReturn(pageable);
		when(productJpaAdapter.findProductsIdsByLangAndFilters(lang, filter, pageable)).thenReturn(ids);
		when(productJpaAdapter.findProductsByIds(lang, ids.getContent(), pageable.getSort()))
				.thenReturn(singletonList(productEntity));
		when(imageRepository.getImageLinkByName(imageName)).thenReturn(TEST_IMAGE_LINK);
		when(productPageMapper.toDomain(any(PageImpl.class))).thenReturn(productPage);

		var actual = productRepository.findAllByLanguageWithFilter(lang, filter, pageableDomain);
		assertEquals(productPage, actual);

		verify(pageableMapper).fromDomain(pageableDomain);
		verify(productJpaAdapter).findProductsIdsByLangAndFilters(lang, filter, pageable);
		verify(productJpaAdapter).findProductsByIds(lang, ids.getContent(), pageable.getSort());
		verify(productPageMapper).toDomain(any(PageImpl.class));
		verify(imageRepository).getImageLinkByName(imageName);
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
}
