package com.academy.orders.infrastructure.product;

import com.academy.orders.domain.product.entity.ProductTranslation;
import com.academy.orders.infrastructure.language.entity.LanguageEntity;
import com.academy.orders.infrastructure.product.entity.ProductTranslationEntity;
import java.util.Collection;
import org.hibernate.Hibernate;
import org.mapstruct.Condition;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductTranslationMapper {
	ProductTranslation fromEntity(ProductTranslationEntity translationEntity);

	ProductTranslationEntity toEntity(ProductTranslation productTranslation);

	@Condition
	default boolean isNotLazyLoadedLanguageEntity(LanguageEntity source) {
		return Hibernate.isInitialized(source);
	}
}
