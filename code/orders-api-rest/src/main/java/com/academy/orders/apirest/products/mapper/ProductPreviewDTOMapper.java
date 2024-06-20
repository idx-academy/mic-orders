package com.academy.orders.apirest.products.mapper;

import com.academy.orders.domain.product.entity.Product;
import com.academy.orders_api_rest.generated.model.ProductPreviewDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductPreviewDTOMapper {
	ProductPreviewDTO toDto(Product product);
}
