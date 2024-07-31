package com.academy.orders.infrastructure.product;

import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.ProductTranslation;
import com.academy.orders.infrastructure.product.entity.ProductEntity;
import com.academy.orders.infrastructure.product.entity.ProductTranslationEntity;
import com.academy.orders.infrastructure.tag.entity.TagEntity;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.academy.orders.infrastructure.ModelUtils.getProductTranslationEntity;
import static com.academy.orders.infrastructure.ModelUtils.getTagEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductMapperTest {
	@Spy
	private ProductMapper productMapper;

	@Test
	void fromProductTranslationEntityTest() {
		ProductTranslationEntity translationEntity = getProductTranslationEntity();
		ProductEntity productEntity = translationEntity.getProduct();
		productEntity.setProductTranslations(Set.of(translationEntity));
		Product product = Product
				.builder().id(productEntity.getId()).status(productEntity.getStatus()).image(productEntity.getImage())
				.createdAt(productEntity.getCreatedAt()).quantity(productEntity.getQuantity())
				.price(productEntity.getPrice()).productTranslations(Set.of(ProductTranslation.builder()
						.name(translationEntity.getName()).description(translationEntity.getDescription()).build()))
				.build();

		when(productMapper.fromEntity(productEntity)).thenReturn(product);

		Product actual = productMapper.fromEntity(translationEntity);

		assertEquals(product, actual);
		verify(productMapper).fromEntity(productEntity);
	}

	@Test
	void isNotLazyLoadedTagEntityTrueTest() {
		Collection<TagEntity> collection = List.of(getTagEntity());
		try (MockedStatic<Hibernate> hibernateMockedStatic = mockStatic(Hibernate.class)) {
			hibernateMockedStatic.when(() -> Hibernate.isInitialized(collection)).thenReturn(true);
			assertTrue(productMapper.isNotLazyLoadedTagEntity(collection));
		}
	}

	@Test
	void isNotLazyLoadedTagEntityFalseTest() {
		Collection<TagEntity> collection = List.of(getTagEntity());
		try (MockedStatic<Hibernate> hibernateMockedStatic = mockStatic(Hibernate.class)) {
			hibernateMockedStatic.when(() -> Hibernate.isInitialized(collection)).thenReturn(false);
			assertFalse(productMapper.isNotLazyLoadedTagEntity(collection));
		}
	}
}
