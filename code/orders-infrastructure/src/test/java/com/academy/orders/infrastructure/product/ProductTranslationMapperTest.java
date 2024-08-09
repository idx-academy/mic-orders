package com.academy.orders.infrastructure.product;

import com.academy.orders.domain.product.entity.ProductTranslation;
import com.academy.orders.infrastructure.language.entity.LanguageEntity;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.academy.orders.infrastructure.ModelUtils.getLanguageEntity;
import static com.academy.orders.infrastructure.ModelUtils.getProductTranslationEntity;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ProductTranslationMapperTest {
	private ProductTranslationMapper productTranslationMapper;

	@BeforeEach
	void setUp() {
		productTranslationMapper = Mappers.getMapper(ProductTranslationMapper.class);
	}

	@Test
	void mapProductTranslationEntityToProductTranslationTest() {
		var languageEntity = getLanguageEntity();
		var productTranslationEntity = getProductTranslationEntity();

		ProductTranslation result = productTranslationMapper.fromEntity(productTranslationEntity);

		Assertions.assertEquals("Name", result.name());
		Assertions.assertEquals("Description", result.description());
		Assertions.assertEquals(languageEntity.getId(), result.language().id());
		Assertions.assertEquals(languageEntity.getCode(), result.language().code());
	}

	@Test
	void returnTrueForInitializedLanguageEntityTest() {
		var languageEntity = getLanguageEntity();

		boolean result = productTranslationMapper.isNotLazyLoadedLanguageEntity(languageEntity);

		assertTrue(result);
	}

	@Test
	void returnFalseForUninitializedLanguageEntityTest() {
		LanguageEntity uninitializedLanguageEntity = new LanguageEntity();

		try (MockedStatic<Hibernate> mockedHibernate = Mockito.mockStatic(Hibernate.class)) {
			mockedHibernate.when(() -> Hibernate.isInitialized(uninitializedLanguageEntity)).thenReturn(false);

			boolean result = productTranslationMapper.isNotLazyLoadedLanguageEntity(uninitializedLanguageEntity);
			assertFalse(result);
		}
	}

	@Test
	void detectPartialInitializationTest() {
		LanguageEntity partialLanguageEntity = new LanguageEntity();
		partialLanguageEntity.setCode("EN");

		try (MockedStatic<Hibernate> mockedHibernate = Mockito.mockStatic(Hibernate.class)) {
			mockedHibernate.when(() -> Hibernate.isInitialized(partialLanguageEntity)).thenReturn(false);

			boolean result = productTranslationMapper.isNotLazyLoadedLanguageEntity(partialLanguageEntity);

			assertFalse(result);
		}
	}
}
