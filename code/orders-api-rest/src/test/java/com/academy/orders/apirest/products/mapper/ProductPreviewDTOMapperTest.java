package com.academy.orders.apirest.products.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static com.academy.orders.apirest.ModelUtils.getProduct;
import static com.academy.orders.apirest.ModelUtils.getProductWithEmptyTags;
import static com.academy.orders.apirest.ModelUtils.getProductWithEmptyTranslations;
import static com.academy.orders.apirest.ModelUtils.getProductWithNullTranslations;
import static com.academy.orders.apirest.TestConstants.PRODUCT_DESCRIPTION;
import static com.academy.orders.apirest.TestConstants.PRODUCT_NAME;
import static com.academy.orders.apirest.TestConstants.TAG_NAME;

class ProductPreviewDTOMapperTest {
	private ProductPreviewDTOMapper productPreviewDTOMapper;

	@BeforeEach
	void setUp() {
		productPreviewDTOMapper = Mappers.getMapper(ProductPreviewDTOMapper.class);
	}

	@Test
	void testToDtoWithValidProduct() {
		var product = getProduct();
		var dto = productPreviewDTOMapper.toDto(product);

		Assertions.assertEquals(PRODUCT_NAME, dto.getName());
		Assertions.assertEquals(PRODUCT_DESCRIPTION, dto.getDescription());
		Assertions.assertEquals(1, dto.getTags().size());
		Assertions.assertEquals(TAG_NAME, dto.getTags().get(0));
	}

	@Test
	void testToDtoWithEmptyTranslations() {
		var product = getProductWithEmptyTranslations();
		var dto = productPreviewDTOMapper.toDto(product);

		Assertions.assertNull(dto.getName());
		Assertions.assertNull(dto.getDescription());
		Assertions.assertEquals(1, dto.getTags().size());
		Assertions.assertEquals(TAG_NAME, dto.getTags().get(0));
	}

	@Test
	void testToDtoWithEmptyTags() {
		var product = getProductWithEmptyTags();
		var dto = productPreviewDTOMapper.toDto(product);

		Assertions.assertEquals(PRODUCT_NAME, dto.getName());
		Assertions.assertEquals(PRODUCT_DESCRIPTION, dto.getDescription());
		Assertions.assertEquals(0, dto.getTags().size());
	}

	@Test
	void testToDtoWithNullTranslations() {
		var product = getProductWithNullTranslations();
		var dto = productPreviewDTOMapper.toDto(product);

		Assertions.assertNull(dto.getName());
		Assertions.assertNull(dto.getDescription());
		Assertions.assertEquals(1, dto.getTags().size());
		Assertions.assertEquals(TAG_NAME, dto.getTags().get(0));
	}

	@Test
	void testToDtoWithEmptyProductTranslations() {
		var product = getProductWithNullTranslations();
		var dto = productPreviewDTOMapper.toDto(product);

		Assertions.assertNull(dto.getName());
		Assertions.assertNull(dto.getDescription());
		Assertions.assertEquals(1, dto.getTags().size());
		Assertions.assertEquals(TAG_NAME, dto.getTags().get(0));
	}
}
