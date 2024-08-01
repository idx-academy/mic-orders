package com.academy.orders.apirest.products.mapper;

import com.academy.orders.domain.product.dto.ProductRequestDto;
import com.academy.orders_api_rest.generated.model.ProductRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductRequestDTOMapper {
	ProductRequestDto fromDTO(ProductRequestDTO createProductRequestDTO);
}
