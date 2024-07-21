package com.academy.orders.apirest.products.mapper;

import com.academy.orders.domain.product.entity.UpdateProduct;
import com.academy.orders_api_rest.generated.model.UpdateProductRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateProductRequestDTOMapper {
	UpdateProduct fromDTO(UpdateProductRequestDTO dto);
}
