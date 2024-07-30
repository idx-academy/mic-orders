package com.academy.orders.infrastructure.product;

import com.academy.orders.domain.product.dto.CreateProductRequestDto;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.infrastructure.product.entity.ProductEntity;
import com.academy.orders.infrastructure.tag.TagMapper;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TagMapper.class, ProductTranslationMapper.class})
public interface ProductMapper {
	Product fromEntity(ProductEntity productEntity);
	List<Product> fromEntities(List<ProductEntity> productEntities);

	ProductEntity toEntity(Product product);

	@Mapping(target = "id", ignore = true)
	ProductEntity toEntity(CreateProductRequestDto dto);

	@Mapping(target = "image", source = "image")
	Product mapDomainImage(Product product, String image);
}
