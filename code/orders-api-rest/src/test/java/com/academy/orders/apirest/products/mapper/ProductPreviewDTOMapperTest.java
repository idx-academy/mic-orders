package com.academy.orders.apirest.products.mapper;

import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.Tag;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders_api_rest.generated.model.ProductPreviewDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.academy.orders.apirest.ModelUtils.getProduct;
import static com.academy.orders.apirest.ModelUtils.getProductWithEmptyTags;
import static com.academy.orders.apirest.ModelUtils.getProductWithEmptyTranslations;
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
		Product product = Product.builder()
				.id(UUID.randomUUID())
				.status(ProductStatus.AVAILABLE)
				.image("image_url")
				.createdAt(LocalDateTime.now())
				.quantity(10)
				.price(BigDecimal.valueOf(100.0))
				.tags(Set.of(new Tag(1L, TAG_NAME)))
				.productTranslations(null)
				.build();

		ProductPreviewDTO dto = productPreviewDTOMapper.toDto(product);

		Assertions.assertNull(dto.getName());
		Assertions.assertNull(dto.getDescription());
		Assertions.assertEquals(1, dto.getTags().size());
		Assertions.assertEquals(TAG_NAME, dto.getTags().get(0));
	}

	@Test
	void testToDtoWithEmptyProductTranslations() {
		Product product = Product.builder()
				.id(UUID.randomUUID())
				.status(ProductStatus.AVAILABLE)
				.image("image_url")
				.createdAt(LocalDateTime.now())
				.quantity(10)
				.price(BigDecimal.valueOf(100.0))
				.tags(Set.of(new Tag(1L, TAG_NAME)))
				.productTranslations(Collections.emptySet())
				.build();

		ProductPreviewDTO dto = productPreviewDTOMapper.toDto(product);

		Assertions.assertNull(dto.getName());
		Assertions.assertNull(dto.getDescription());
		Assertions.assertEquals(1, dto.getTags().size());
		Assertions.assertEquals(TAG_NAME, dto.getTags().get(0));
	}
}
