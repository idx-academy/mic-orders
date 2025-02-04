package com.academy.orders.apirest.products.mapper;

import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.Tag;
import com.academy.orders_api_rest.generated.model.ProductDetailsResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;

import static com.academy.orders.apirest.ModelUtils.*;
import static com.academy.orders.apirest.TestConstants.PRODUCT_DESCRIPTION;
import static com.academy.orders.apirest.TestConstants.PRODUCT_NAME;
import static com.academy.orders.apirest.TestConstants.TAG_NAME;
import static org.junit.jupiter.api.Assertions.*;

class ProductDetailsResponseDTOMapperTest {
	private ProductDetailsResponseDTOMapper productDetailsResponseDTOMapper;

	@BeforeEach
	void setUp() {
		productDetailsResponseDTOMapper = Mappers.getMapper(ProductDetailsResponseDTOMapper.class);
	}

	@Test
	void toDtoWithValidProductTest() {
		var product = getProduct();
		var dto = productDetailsResponseDTOMapper.toDTO(product);

		assertEquals(PRODUCT_NAME, dto.getName());
		assertEquals(PRODUCT_DESCRIPTION, dto.getDescription());
		assertEquals(product.getImage(), dto.getImage());
		assertIterableEquals(product.getTags().stream().map(Tag::name).toList(), dto.getTags());
		assertEquals(product.getQuantity(), dto.getQuantity());
		assertEquals(product.getPrice(), dto.getPrice());
		assertNull(dto.getDiscount());
		assertNull(dto.getPriceWithDiscount());
	}

	@Test
	void toDtoWithDiscountTest() {
		var product = getProductWithDiscount();
		var dto = productDetailsResponseDTOMapper.toDTO(product);

		assertEquals(PRODUCT_NAME, dto.getName());
		assertEquals(PRODUCT_DESCRIPTION, dto.getDescription());
		assertEquals(product.getImage(), dto.getImage());
		assertIterableEquals(product.getTags().stream().map(Tag::name).toList(), dto.getTags());
		assertEquals(product.getQuantity(), dto.getQuantity());
		assertEquals(product.getPrice(), dto.getPrice());
		assertEquals(product.getDiscount().getAmount(), dto.getDiscount());
		assertEquals(product.getPriceWithDiscount(), dto.getPriceWithDiscount());
	}

	@Test
	void toDtoWithEmptyTranslationsTest() {
		var product = getProductWithEmptyTranslations();
		var dto = productDetailsResponseDTOMapper.toDTO(product);

		assertNull(dto.getName());
		assertNull(dto.getDescription());
		assertEquals(product.getImage(), dto.getImage());
		assertEquals(1, dto.getTags().size());
		assertEquals(TAG_NAME, dto.getTags().get(0));
		assertEquals(product.getQuantity(), dto.getQuantity());
		assertEquals(product.getPrice(), dto.getPrice());
		assertNull(dto.getDiscount());
		assertNull(dto.getPriceWithDiscount());
	}

	@Test
	void toDtoWithEmptyTagsTest() {
		var product = getProductWithEmptyTags();
		var dto = productDetailsResponseDTOMapper.toDTO(product);

		assertEquals(PRODUCT_NAME, dto.getName());
		assertEquals(PRODUCT_DESCRIPTION, dto.getDescription());
		assertEquals(product.getImage(), dto.getImage());
		assertEquals(Collections.EMPTY_LIST, dto.getTags());
		assertEquals(product.getQuantity(), dto.getQuantity());
		assertEquals(product.getPrice(), dto.getPrice());
		assertNull(dto.getDiscount());
		assertEquals(product.getPriceWithDiscount(), dto.getPriceWithDiscount());
	}
}
