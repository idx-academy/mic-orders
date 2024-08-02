package com.academy.orders.apirest.products.mapper;

import com.academy.orders.domain.product.entity.Language;
import com.academy.orders.domain.product.entity.ProductTranslation;
import com.academy.orders_api_rest.generated.model.ProductTranslationDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

class ProductResponseDTOMapperTest {
	private ProductResponseDTOMapper productResponseDTOMapper;

	@BeforeEach
	void setUp() {
		productResponseDTOMapper = Mappers.getMapper(ProductResponseDTOMapper.class);
	}

	@Test
	void mapProductTranslationsTest() {
		ProductTranslation translation = ProductTranslation.builder().name("Product").description("Description")
				.language(new Language(1L, "en")).build();

		Set<ProductTranslation> productTranslations = Set.of(translation);

		List<ProductTranslationDTO> productTranslationDTOs = productResponseDTOMapper
				.mapProductTranslations(productTranslations);

		Assertions.assertNotNull(productTranslationDTOs);
		Assertions.assertEquals(1, productTranslationDTOs.size());

		ProductTranslationDTO translationDTO = productTranslationDTOs.get(0);
		Assertions.assertEquals(translation.name(), translationDTO.getName());
		Assertions.assertEquals(translation.description(), translationDTO.getDescription());
		Assertions.assertEquals(translation.language().code(), translationDTO.getLanguageCode());
	}

	@Test
	void mapLocalDateTimeToOffsetDateTimeTest() {
		LocalDateTime localDateTime = LocalDateTime.now();
		OffsetDateTime offsetDateTime = productResponseDTOMapper.mapLocalDateTimeToOffsetDateTime(localDateTime);

		Assertions.assertEquals(localDateTime.atOffset(ZoneOffset.UTC), offsetDateTime);
	}

	@Test
	void mapLocalDateTimeToOffsetDateTimeWithNullTest() {
		OffsetDateTime offsetDateTime = productResponseDTOMapper.mapLocalDateTimeToOffsetDateTime(null);

		Assertions.assertNull(offsetDateTime);
	}
}
