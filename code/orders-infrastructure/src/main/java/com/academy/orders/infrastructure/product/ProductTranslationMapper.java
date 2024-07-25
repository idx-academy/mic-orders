package com.academy.orders.infrastructure.product;

import com.academy.orders.domain.product.entity.ProductTranslation;
import com.academy.orders.infrastructure.product.entity.ProductTranslationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductTranslationMapper {
	ProductTranslation fromEntity(ProductTranslationEntity translationEntity);

	ProductTranslationEntity toEntity(ProductTranslation productTranslation);
}
