package com.academy.orders.infrastructure.product;

import com.academy.orders.domain.product.dto.ProductRequestDto;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.infrastructure.product.entity.ProductEntity;
import com.academy.orders.infrastructure.product.entity.ProductTranslationEntity;
import com.academy.orders.infrastructure.tag.TagMapper;
import com.academy.orders.infrastructure.tag.entity.TagEntity;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.hibernate.Hibernate;
import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {TagMapper.class, ProductTranslationMapper.class})
public interface ProductMapper {
	Product fromEntity(ProductEntity productEntity);

	default Product fromEntity(ProductTranslationEntity translationEntity) {
		ProductEntity product = translationEntity.getProduct();
		product.setProductTranslations(Set.of(translationEntity));
		return fromEntity(product);
	}

	List<Product> fromEntities(List<ProductEntity> productEntities);

	ProductEntity toEntity(Product product);

	@Mapping(target = "id", ignore = true)
	ProductEntity toEntity(ProductRequestDto dto);

	@Condition
	default boolean isNotLazyLoadedTagEntity(Collection<TagEntity> source) {
		return Hibernate.isInitialized(source);
	}

	@Named("mapDomainImage")
	@Mapping(target = "image", source = "image")
	Product mapDomainImage(Product product, String image);
}
