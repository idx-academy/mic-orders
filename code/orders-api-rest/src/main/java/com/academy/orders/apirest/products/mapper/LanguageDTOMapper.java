package com.academy.orders.apirest.products.mapper;

import com.academy.orders_api_rest.generated.model.LanguageDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LanguageDTOMapper {
	LanguageDTO fromDTO(LanguageDTO languageDTO);
}
