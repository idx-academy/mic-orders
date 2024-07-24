package com.academy.orders.infrastructure.product;

import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.infrastructure.common.PageableMapper;
import com.academy.orders.infrastructure.product.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, PageableMapper.class})
public interface ProductPageMapper {
	com.academy.orders.domain.common.Page<Product> toDomain(Page<ProductEntity> page);
}
