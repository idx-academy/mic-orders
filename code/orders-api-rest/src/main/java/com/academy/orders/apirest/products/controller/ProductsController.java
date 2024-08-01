package com.academy.orders.apirest.products.controller;

import com.academy.orders.apirest.common.mapper.PageableDTOMapper;
import com.academy.orders.apirest.products.mapper.PageProductSearchResultDTOMapper;
import com.academy.orders.apirest.products.mapper.ProductPreviewDTOMapper;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.usecase.GetAllProductsUseCase;
import com.academy.orders.domain.product.usecase.GetProductSearchResultsUseCase;
import com.academy.orders_api_rest.generated.api.ProductsApi;
import com.academy.orders_api_rest.generated.model.PageProductSearchResultDTO;
import com.academy.orders_api_rest.generated.model.PageProductsDTO;
import com.academy.orders_api_rest.generated.model.PageableDTO;
import com.academy.orders_api_rest.generated.model.ProductPreviewDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class ProductsController implements ProductsApi {
	private final GetAllProductsUseCase getAllProductsUseCase;
	private final GetProductSearchResultsUseCase getProductSearchResultsUseCase;
	private final ProductPreviewDTOMapper productPreviewDTOMapper;
	private final PageableDTOMapper pageableDTOMapper;
	private final PageProductSearchResultDTOMapper pageProductSearchResultDTOMapper;

	@Override
	public PageProductsDTO getProducts(String lang, PageableDTO dto) {
		log.debug("Get all products by language code: {}", lang);
		var pageable = pageableDTOMapper.fromDto(dto);
		var products = getAllProductsUseCase.getAllProducts(lang, pageable);

		List<ProductPreviewDTO> productPreviews = products.content().stream().map(productPreviewDTOMapper::toDto)
				.toList();
		return buildPageProductsDTO(productPreviews, products);
	}

	@Override
	public PageProductSearchResultDTO searchProducts(String searchQuery, String lang, PageableDTO pageable) {
		Pageable pageableDomain = pageableDTOMapper.fromDto(pageable);
		var products = getProductSearchResultsUseCase.findProductsBySearchQuery(searchQuery, lang, pageableDomain);
		return pageProductSearchResultDTOMapper.toDto(products);
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
