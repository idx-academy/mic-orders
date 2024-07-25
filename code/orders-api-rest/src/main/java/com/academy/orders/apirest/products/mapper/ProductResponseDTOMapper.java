package com.academy.orders.apirest.products.mapper;

import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.ProductTranslation;
import com.academy.orders_api_rest.generated.model.ProductResponseDTO;
import com.academy.orders_api_rest.generated.model.ProductTranslationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ProductResponseDTOMapper {
	@Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "mapLocalDateTimeToOffsetDateTime")
	@Mapping(target = "productTranslations", qualifiedByName = "mapProductTranslations")
	ProductResponseDTO toDTO(Product product);

	@Named("mapLocalDateTimeToOffsetDateTime")
	default OffsetDateTime mapLocalDateTimeToOffsetDateTime(LocalDateTime localDateTime) {
		return localDateTime == null ? null : localDateTime.atOffset(ZoneOffset.UTC);
	}

	@Named("mapProductTranslations")
	default List<ProductTranslationDTO> mapProductTranslations(Set<ProductTranslation> productTranslations) {
		return productTranslations.stream().map(this::toProductTranslationDTO).toList();
	}

	@Mapping(source = "language.code", target = "languageCode")
	ProductTranslationDTO toProductTranslationDTO(ProductTranslation productTranslation);
}
