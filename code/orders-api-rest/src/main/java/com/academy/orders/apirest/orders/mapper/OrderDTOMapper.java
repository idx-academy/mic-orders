package com.academy.orders.apirest.orders.mapper;

import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.ProductTranslation;
import com.academy.orders.domain.product.entity.Tag;
import com.academy.orders_api_rest.generated.model.OrderDTO;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import com.academy.orders.domain.order.dto.CreateOrderDto;
import com.academy.orders_api_rest.generated.model.PlaceOrderRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OrderDTOMapper {

	OrderDTO toDto(Order order);

	default OffsetDateTime map(LocalDateTime value) {
		return OffsetDateTime.of(value, ZoneOffset.UTC);
	}

	@Mapping(target = "name", source = "product.productTranslations", qualifiedByName = "mapProductName")
	@Mapping(target = "description", source = "product.productTranslations", qualifiedByName = "mapProductDescription")
	@Mapping(source = "product.tags", target = "tags", qualifiedByName = "mapTags")
	com.academy.orders_api_rest.generated.model.ProductPreviewDTO toDto(Product product);

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
		return tags.stream().map(Tag::name).toList();
	}

    CreateOrderDto toCreateOrderDto(PlaceOrderRequestDTO placeOrderRequestDTO);
}
