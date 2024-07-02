package com.academy.orders.infrastructure.product;

import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.infrastructure.product.entity.ProductEntity;
import com.academy.orders.infrastructure.tag.TagMapper;
import org.mapstruct.Mapper;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {TagMapper.class,
		ProductTranslationMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
	Product fromEntity(ProductEntity productEntity);
	List<Product> fromEntities(List<ProductEntity> productEntities);

	@Named("fromEntityWithoutJoin")
	@Mapping(target = "tags", ignore = true)
	@Mapping(target = "productTranslations", ignore = true)
	Product fromEntityWithoutJoin(ProductEntity productEntities);

	@IterableMapping(qualifiedByName = "fromEntityWithoutJoin")
	List<Product> fromEntitiesWithoutJoin(List<ProductEntity> productEntities);
}
