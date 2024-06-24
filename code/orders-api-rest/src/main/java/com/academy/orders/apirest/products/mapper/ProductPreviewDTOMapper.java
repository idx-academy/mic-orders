package com.academy.orders.apirest.products.mapper;

import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.ProductTranslation;
import com.academy.orders.domain.product.entity.Tag;
import com.academy.orders_api_rest.generated.model.ProductPreviewDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductPreviewDTOMapper {

	@Mapping(target = "name", source = "product.productTranslations", qualifiedByName = "mapProductName")
	@Mapping(target = "description", source = "product.productTranslations", qualifiedByName = "mapProductDescription")
	@Mapping(source = "product.tags", target = "tags", qualifiedByName = "mapTags")
	ProductPreviewDTO toDto(Product product);

	@Named("mapProductName")
	default String mapProductName(Set<ProductTranslation> productTranslations) {
		if (productTranslations != null && !productTranslations.isEmpty()) {
			return productTranslations.iterator().next().name();
		}
		return null;
	}

	@Named("mapProductDescription")
	default String mapProductDescription(Set<ProductTranslation> productTranslations) {
		if (productTranslations != null && !productTranslations.isEmpty()) {
			return productTranslations.iterator().next().description();
		}
		return null;
	}

	@Named("mapTags")
	default List<String> mapTags(Set<Tag> tags) {
		return tags.stream().map(Tag::name).collect(Collectors.toList());
	}
}
