package com.academy.orders.apirest.products.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static com.academy.orders.apirest.ModelUtils.getProduct;
import static com.academy.orders.apirest.TestConstants.PRODUCT_DESCRIPTION;
import static com.academy.orders.apirest.TestConstants.PRODUCT_NAME;
import static com.academy.orders.apirest.TestConstants.TAG_NAME;

public class ProductPreviewDTOMapperTest {
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
}
