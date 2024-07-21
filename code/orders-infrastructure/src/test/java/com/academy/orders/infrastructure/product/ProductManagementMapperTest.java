package com.academy.orders.infrastructure.product;

import com.academy.orders.infrastructure.product.entity.ProductEntity;
import com.academy.orders.infrastructure.product.entity.ProductTranslationEntity;
import com.academy.orders.infrastructure.product.entity.ProductTranslationId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.util.Set;
import java.util.UUID;

import static com.academy.orders.infrastructure.ModelUtils.getProductManagement;
import static com.academy.orders.infrastructure.ModelUtils.getProductTranslationManagement;

class ProductManagementMapperTest {
	private ProductManagementMapper productManagementMapper;

	@BeforeEach
	public void setUp() {
		productManagementMapper = Mappers.getMapper(ProductManagementMapper.class);
	}

	@Test
	public void testFromEntity() {
		var productEntity = new ProductEntity();
		productEntity.setId(UUID.randomUUID());

		var translationEntity = new ProductTranslationEntity();
		translationEntity.setProductTranslationId(new ProductTranslationId(productEntity.getId(), 1L));
		translationEntity.setName("Test Translation");
		translationEntity.setDescription("Test Description");

		productEntity.setProductTranslations(Set.of(translationEntity));

		var productManagement = productManagementMapper.fromEntity(productEntity);
		Assertions.assertNotNull(productManagement);
		Assertions.assertEquals(productEntity.getId(), productManagement.id());
	}

	@Test
	public void testToEntity() {
		var productTranslationManagement = getProductTranslationManagement();
		var productManagement = getProductManagement();
		var productEntity = productManagementMapper.toEntity(productManagement);
		var translationEntity = productEntity.getProductTranslations().iterator().next();

		Assertions.assertNotNull(productEntity);
		Assertions.assertEquals(productManagement.id(), productEntity.getId());
		Assertions.assertNotNull(productEntity.getProductTranslations());
		Assertions.assertEquals(1, productEntity.getProductTranslations().size());
		Assertions.assertEquals(productTranslationManagement.productId(),
				translationEntity.getProductTranslationId().getProductId());
		Assertions.assertEquals(productTranslationManagement.languageId(),
				translationEntity.getProductTranslationId().getLanguageId());
		Assertions.assertEquals(productTranslationManagement.name(), translationEntity.getName());
		Assertions.assertEquals(productTranslationManagement.description(), translationEntity.getDescription());
	}

	@Test
	public void mapProductTranslationManagementWithNullTest() {
		Set<ProductTranslationEntity> result = productManagementMapper.mapProductTranslationManagement(null);
		Assertions.assertNotNull(result);
		Assertions.assertTrue(result.isEmpty());
	}

	@Test
	public void productTranslationManagementWithNullTest() {
		ProductTranslationEntity result = productManagementMapper.productTranslationManagement(null);
		Assertions.assertNull(result);
	}
}
