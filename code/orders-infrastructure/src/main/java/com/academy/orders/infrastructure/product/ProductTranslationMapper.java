package com.academy.orders.infrastructure.product;

import com.academy.orders.domain.product.entity.ProductTranslation;
import com.academy.orders.infrastructure.product.entity.ProductTranslationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductTranslationMapper {
	@Mapping(target = "language", ignore = true)
	ProductTranslation fromEntity(ProductTranslationEntity translationEntity);
}
