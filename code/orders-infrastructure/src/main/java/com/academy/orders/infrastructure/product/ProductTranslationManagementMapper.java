package com.academy.orders.infrastructure.product;

import com.academy.orders.domain.product.entity.ProductTranslationManagement;
import com.academy.orders.infrastructure.product.entity.ProductTranslationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductTranslationManagementMapper {
	@Mapping(source = "productTranslationId.productId", target = "productId")
	@Mapping(source = "productTranslationId.languageId", target = "languageId")
	ProductTranslationManagement fromEntity(ProductTranslationEntity productTranslationEntity);
}
