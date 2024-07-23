package com.academy.orders.apirest.products.mapper;

import com.academy.orders.domain.product.entity.Product;
import com.academy.orders_api_rest.generated.model.ProductResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface ProductResponseDTOMapper {
	@Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "mapLocalDateTimeToOffsetDateTime")
	ProductResponseDTO toDTO(Product product);

	@Named("mapLocalDateTimeToOffsetDateTime")
	default OffsetDateTime mapLocalDateTimeToOffsetDateTime(LocalDateTime localDateTime) {
		return localDateTime == null ? null : localDateTime.atOffset(ZoneOffset.UTC);
	}
}
