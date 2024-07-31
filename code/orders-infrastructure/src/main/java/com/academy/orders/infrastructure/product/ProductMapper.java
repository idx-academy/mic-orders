package com.academy.orders.infrastructure.product;

import com.academy.orders.domain.product.dto.CreateProductRequestDto;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.infrastructure.language.entity.LanguageEntity;
import com.academy.orders.infrastructure.product.entity.ProductEntity;
import com.academy.orders.infrastructure.tag.TagMapper;
import com.academy.orders.infrastructure.tag.entity.TagEntity;
import java.util.Collection;
import java.util.List;
import org.hibernate.Hibernate;
import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TagMapper.class, ProductTranslationMapper.class})
public interface ProductMapper {
	Product fromEntity(ProductEntity productEntity);
	List<Product> fromEntities(List<ProductEntity> productEntities);

	ProductEntity toEntity(Product product);

	@Mapping(target = "id", ignore = true)
	ProductEntity toEntity(CreateProductRequestDto dto);

	@Condition
	default boolean isNotLazyLoadedTagEntity(Collection<TagEntity> source) {
		return Hibernate.isInitialized(source);
	}
}
