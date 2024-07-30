package com.academy.orders.apirest.products.mapper;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders_api_rest.generated.model.PageProductSearchResultDTO;
import com.academy.orders_api_rest.generated.model.ProductSearchResultDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PageProductSearchResultDTOMapper {
    PageProductSearchResultDTO toDto(Page<Product> productPage);

    @Mapping(target = "name", source = "product.productTranslations", qualifiedByName = "mapProductName")
    ProductSearchResultDTO map(Product product);
}
