package com.academy.orders.apirest.common.mapper;

import com.academy.orders.domain.common.Pageable;
import com.academy.orders_api_rest.generated.model.PageableDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PageableDTOMapper {
	Pageable fromDto(PageableDTO pageableDTO);
}
