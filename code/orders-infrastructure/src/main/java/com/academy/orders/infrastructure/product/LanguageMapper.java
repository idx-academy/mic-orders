package com.academy.orders.infrastructure.product;

import com.academy.orders.domain.product.entity.Language;
import com.academy.orders.infrastructure.language.entity.LanguageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LanguageMapper {
	Language fromEntity(LanguageEntity languageEntity);
}
