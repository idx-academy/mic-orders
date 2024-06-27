package com.academy.orders.apirest.products.controller;

import com.academy.orders.apirest.common.mapper.PageableDTOMapper;
import com.academy.orders.apirest.products.mapper.ProductPreviewDTOMapper;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.usecase.GetAllProductsUseCase;
import com.academy.orders_api_rest.generated.api.ProductsApi;
import com.academy.orders_api_rest.generated.model.PageProductsDTO;
import com.academy.orders_api_rest.generated.model.PageableDTO;
import com.academy.orders_api_rest.generated.model.ProductPreviewDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class ProductController implements ProductsApi {
	private final GetAllProductsUseCase getAllProductsUseCase;
	private final ProductPreviewDTOMapper productPreviewDTOMapper;
	private final PageableDTOMapper pageableDTOMapper;

	@Override
	public PageProductsDTO getProducts(String lang, PageableDTO dto, String sort) {
		log.debug("Get all products by language code: {}", lang);
		var pageable = pageableDTOMapper.fromDto(dto);
		var products = getAllProductsUseCase.getAllProducts(lang, pageable, sort);

		List<ProductPreviewDTO> productPreviews = products.content().stream().map(productPreviewDTOMapper::toDto)
				.toList();
		return buildPageProductsDTO(productPreviews, products);
	}

	private PageProductsDTO buildPageProductsDTO(List<ProductPreviewDTO> productPreviews, Page<Product> productPage) {
		var response = new PageProductsDTO();
		response.setContent(productPreviews);
		response.setTotalElements(productPage.totalElements());
		response.totalPages(productPage.totalPages());
		response.first(productPage.first());
		response.last(productPage.last());
		response.number(productPage.number());
		response.numberOfElements(productPage.numberOfElements());
		response.size(productPage.size());
		response.empty(productPage.empty());
		return response;
	}
}
