package com.academy.orders.infrastructure.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.util.Set;

import static com.academy.orders.infrastructure.ModelUtils.getProductTranslationEntity;
import static com.academy.orders.infrastructure.ModelUtils.getProductTranslationManagement;

class ProductTranslationManagementMapperTest {
	private ProductTranslationManagementMapper mapper;

	@BeforeEach
	void setUp() {
		mapper = Mappers.getMapper(ProductTranslationManagementMapper.class);
	}

	@Test
	void fromEntityTest() {
		var productTranslationEntity = getProductTranslationEntity();
		var expected = getProductTranslationManagement();
		var result = mapper.fromEntity(productTranslationEntity);
		Assertions.assertEquals(expected, result);
	}

	@Test
	void fromEntitiesTest() {
		var productTranslationEntity = getProductTranslationEntity();
		var expected = getProductTranslationManagement();
		var result = mapper.fromEntities(Set.of(productTranslationEntity));
		Assertions.assertEquals(Set.of(expected), result);
	}
}
