package com.academy.orders.infrastructure.product;

import com.academy.orders.domain.product.entity.ProductTranslation;
import com.academy.orders.infrastructure.language.LanguageMapper;
import com.academy.orders.infrastructure.product.entity.ProductTranslationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {LanguageMapper.class})
public interface ProductTranslationMapper {
	ProductTranslation fromEntity(ProductTranslationEntity productTranslationEntity);

	ProductTranslationEntity toEntity(ProductTranslation productTranslation);
}
