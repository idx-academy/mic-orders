package com.academy.orders.infrastructure.product;

import com.academy.orders.domain.product.entity.ProductTranslationManagement;
import com.academy.orders.domain.product.entity.ProductManagement;
import com.academy.orders.infrastructure.product.entity.ProductEntity;
import com.academy.orders.infrastructure.product.entity.ProductTranslationEntity;
import com.academy.orders.infrastructure.product.entity.ProductTranslationId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductManagementMapper {

	ProductManagement fromEntity(ProductEntity productEntity);

	@Mappings({@Mapping(source = "productTranslationManagement", target = "productTranslations")})
	ProductEntity toEntity(ProductManagement productWithId);

	default Set<ProductTranslationEntity> mapProductTranslationManagement(
			Set<ProductTranslationManagement> productTranslationManagement) {
		if (productTranslationManagement == null) {
			return Set.of();
		}
		return productTranslationManagement.stream().map(this::productTranslationManagement)
				.collect(Collectors.toSet());
	}

	default ProductTranslationEntity productTranslationManagement(ProductTranslationManagement dto) {
		if (dto == null) {
			return null;
		}
		return ProductTranslationEntity.builder()
				.productTranslationId(new ProductTranslationId(dto.productId(), dto.languageId())).name(dto.name())
				.description(dto.description()).build();
	}
}