package com.academy.orders.apirest.products.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.academy.orders.apirest.ModelUtils.*;
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
	void toDtoWithValidProductTest() {
		var product = getProduct();
		var dto = productPreviewDTOMapper.toDto(product);

		Assertions.assertEquals(PRODUCT_NAME, dto.getName());
		Assertions.assertEquals(PRODUCT_DESCRIPTION, dto.getDescription());
		Assertions.assertEquals(1, dto.getTags().size());
		Assertions.assertEquals(TAG_NAME, dto.getTags().get(0));
		Assertions.assertNull(dto.getDiscount());
		Assertions.assertNull(dto.getPriceWithDiscount());
	}

	@Test
	void toDtoWithDiscount() {
		var product = getProductWithDiscount();
		var dto = productPreviewDTOMapper.toDto(product);

		Assertions.assertEquals(PRODUCT_NAME, dto.getName());
		Assertions.assertEquals(PRODUCT_DESCRIPTION, dto.getDescription());
		Assertions.assertEquals(1, dto.getTags().size());
		Assertions.assertEquals(TAG_NAME, dto.getTags().get(0));
		Assertions.assertEquals(product.getDiscount().getAmount(), dto.getDiscount());
		Assertions.assertEquals(product.getPriceWithDiscount(), dto.getPriceWithDiscount());
	}

	@Test
	void toDtoWithEmptyTranslationsTest() {
		var product = getProductWithEmptyTranslations();
		var dto = productPreviewDTOMapper.toDto(product);

		Assertions.assertNull(dto.getName());
		Assertions.assertNull(dto.getDescription());
		Assertions.assertEquals(1, dto.getTags().size());
		Assertions.assertEquals(TAG_NAME, dto.getTags().get(0));
		Assertions.assertNull(dto.getDiscount());
		Assertions.assertNull(dto.getPriceWithDiscount());
	}

	@Test
	void toDtoWithEmptyTagsTest() {
		var product = getProductWithEmptyTags();
		var dto = productPreviewDTOMapper.toDto(product);

		Assertions.assertEquals(PRODUCT_NAME, dto.getName());
		Assertions.assertEquals(PRODUCT_DESCRIPTION, dto.getDescription());
		Assertions.assertEquals(0, dto.getTags().size());
		Assertions.assertNull(dto.getDiscount());
		Assertions.assertNull(dto.getPriceWithDiscount());
	}

	@Test
	void toDtoWithNullTranslationsTest() {
		var product = getProductWithNullTranslations();
		var dto = productPreviewDTOMapper.toDto(product);

		Assertions.assertNull(dto.getName());
		Assertions.assertNull(dto.getDescription());
		Assertions.assertEquals(1, dto.getTags().size());
		Assertions.assertEquals(TAG_NAME, dto.getTags().get(0));
		Assertions.assertNull(dto.getDiscount());
		Assertions.assertNull(dto.getPriceWithDiscount());
	}
}
