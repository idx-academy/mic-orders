package com.academy.orders.apirest.products.mapper;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders_api_rest.generated.model.PageProductsDTO;
import com.academy.orders_api_rest.generated.model.ProductPreviewDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductPreviewDTOMapper extends ProductMapper {

	@Mapping(target = "name", source = "product.productTranslations", qualifiedByName = "mapProductName")
	@Mapping(target = "description", source = "product.productTranslations", qualifiedByName = "mapProductDescription")
	@Mapping(source = "product.tags", target = "tags", qualifiedByName = "mapTags")
	@Mapping(target = "status", ignore = true) // Ignoring status until impl calculating amount statuses
	@Mapping(target = "discount", source = "discount.amount")
	@Mapping(target = "priceWithDiscount", expression = "java(product.getPriceWithDiscount())")
	ProductPreviewDTO toDto(Product product);

	PageProductsDTO toPageProductsDTO(Page<Product> products);
}
