package com.academy.orders.infrastructure.product;

import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.infrastructure.product.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	Product fromEntity(ProductEntity productEntity);
}
