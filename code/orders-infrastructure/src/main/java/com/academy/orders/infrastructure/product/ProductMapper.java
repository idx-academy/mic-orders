package com.academy.orders.infrastructure.product;

import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.infrastructure.product.entity.ProductEntity;
import com.academy.orders.infrastructure.tag.TagMapper;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring", uses = {TagMapper.class, ProductTranslationMapper.class})
public interface ProductMapper {
	List<Product> fromEntities(List<ProductEntity> productEntities);
}
