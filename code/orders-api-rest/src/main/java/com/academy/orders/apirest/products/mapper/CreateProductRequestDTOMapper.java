package com.academy.orders.apirest.products.mapper;

import com.academy.orders.domain.product.dto.CreateProductRequestDto;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders_api_rest.generated.model.CreateProductRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateProductRequestDTOMapper {
	CreateProductRequestDto fromDTO(CreateProductRequestDTO createProductRequestDTO);
	Product toEntity(CreateProductRequestDTO createProductRequestDTO);
}
