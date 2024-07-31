package com.academy.orders.infrastructure.product;

import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.infrastructure.product.entity.ProductEntity;
import com.academy.orders.infrastructure.product.entity.ProductTranslationEntity;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface ProductPageMapper {
	com.academy.orders.domain.common.Page<Product> toDomain(Page<ProductEntity> page);
	com.academy.orders.domain.common.Page<Product> fromProductTranslationEntity(Page<ProductTranslationEntity> page);
}
