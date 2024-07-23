package com.academy.orders.apirest.products.mapper;

import com.academy.orders.apirest.common.mapper.LocalDateTimeMapper;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.product.dto.ProductManagementFilterDto;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders_api_rest.generated.model.ProductManagementContentDTO;
import com.academy.orders_api_rest.generated.model.ProductManagementFilterDTO;
import com.academy.orders_api_rest.generated.model.ProductManagementPageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = LocalDateTimeMapper.class)
public interface ManagementProductMapper extends ProductMapper {
	ProductManagementFilterDto fromProductManagementFilterDTO(ProductManagementFilterDTO productManagementFilter);

	@Mapping(target = "imageLink", source = "image")
	@Mapping(target = "name", source = "productTranslations", qualifiedByName = "mapProductName")
	@Mapping(target = "tags", source = "tags", qualifiedByName = "mapTags")
	ProductManagementContentDTO fromProduct(Product product);

	ProductManagementPageDTO fromProductPage(Page<Product> productPage);
}
