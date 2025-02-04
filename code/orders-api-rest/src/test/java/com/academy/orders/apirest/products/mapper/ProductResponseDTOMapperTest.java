package com.academy.orders.apirest.products.mapper;

import com.academy.orders.apirest.ModelUtils;
import com.academy.orders.domain.product.entity.Language;
import com.academy.orders.domain.product.entity.ProductTranslation;
import com.academy.orders_api_rest.generated.model.ProductTranslationDTO;
import com.academy.orders_api_rest.generated.model.TagDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProductResponseDTOMapperTest {
	private ProductResponseDTOMapper productResponseDTOMapper;

	@BeforeEach
	void setUp() {
		productResponseDTOMapper = Mappers.getMapper(ProductResponseDTOMapper.class);
	}

	@Test
	void toDto() {
		var product = ModelUtils.getProductWithDiscount();
		var dto = productResponseDTOMapper.toDTO(product);

		assertEquals(product.getId(), dto.getId());
		assertEquals(product.getStatus().name(), dto.getStatus().name());
		assertEquals(product.getImage(), dto.getImage());
		assertEquals(product.getCreatedAt(), dto.getCreatedAt().toLocalDateTime());
		assertEquals(product.getQuantity(), dto.getQuantity());
		assertEquals(product.getPrice(), dto.getPrice());
		assertEquals(product.getDiscount().getAmount(), dto.getDiscount());
		assertEquals(product.getPriceWithDiscount(), dto.getPriceWithDiscount());
		assertIterableEquals(product.getTags().stream().map(o -> new TagDTO().id(o.id()).name(o.name())).toList(),
				dto.getTags());
	}

	@Test
	void mapProductTranslationsTest() {
		ProductTranslation translation = ProductTranslation.builder().name("Product").description("Description")
				.language(new Language(1L, "en")).build();

		Set<ProductTranslation> productTranslations = Set.of(translation);

		List<ProductTranslationDTO> productTranslationDTOs = productResponseDTOMapper
				.mapProductTranslations(productTranslations);

		assertNotNull(productTranslationDTOs);
		assertEquals(1, productTranslationDTOs.size());

		ProductTranslationDTO translationDTO = productTranslationDTOs.get(0);
		assertEquals(translation.name(), translationDTO.getName());
		assertEquals(translation.description(), translationDTO.getDescription());
		assertEquals(translation.language().code(), translationDTO.getLanguageCode());
	}

	@Test
	void mapLocalDateTimeToOffsetDateTimeTest() {
		LocalDateTime localDateTime = LocalDateTime.now();
		OffsetDateTime offsetDateTime = productResponseDTOMapper.mapLocalDateTimeToOffsetDateTime(localDateTime);

		assertEquals(localDateTime.atOffset(ZoneOffset.UTC), offsetDateTime);
	}

	@Test
	void mapLocalDateTimeToOffsetDateTimeWithNullTest() {
		OffsetDateTime offsetDateTime = productResponseDTOMapper.mapLocalDateTimeToOffsetDateTime(null);

		assertNull(offsetDateTime);
	}
}
