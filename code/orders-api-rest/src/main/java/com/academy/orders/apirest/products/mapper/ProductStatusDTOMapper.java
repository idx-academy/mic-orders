package com.academy.orders.apirest.products.mapper;

import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders_api_rest.generated.model.ProductStatusDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductStatusDTOMapper {
	ProductStatus fromDTO(ProductStatusDTO productStatusDTO);
}
