//package com.academy.orders.apirest.products.controller;
//
//import com.academy.orders.apirest.products.mapper.ProductPreviewDTOMapper;
//import com.academy.orders.domain.product.usecase.GetAllProductsUseCase;
//import com.academy.orders_api_rest.generated.api.ProductsApi;
//import com.academy.orders_api_rest.generated.model.ProductPreviewDTO;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//@CrossOrigin
//public class ProductController implements ProductsApi {
//	private final GetAllProductsUseCase getAllProductsUseCase;
//
//	private final ProductPreviewDTOMapper mapper;
//
//	@Override
//	public List<ProductPreviewDTO> getProducts(String language) {
//		log.debug("Get all products by language code: {}", language);
//		return getAllProductsUseCase.getAllProducts(language).stream().map(mapper::toDto).toList();
//	}
//}
package com.academy.orders.apirest.products.controller;

import com.academy.orders.apirest.products.mapper.ProductPreviewDTOMapper;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.usecase.GetAllProductsUseCase;
import com.academy.orders.domain.product.valueobject.PageRequest;
import com.academy.orders.domain.product.valueobject.PageResponse;
import com.academy.orders.domain.product.valueobject.SortOrder;
import com.academy.orders_api_rest.generated.api.ProductsApi;
import com.academy.orders_api_rest.generated.model.GetProducts200ResponseDTO;
import com.academy.orders_api_rest.generated.model.ProductPreviewDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class ProductController implements ProductsApi {
	private final GetAllProductsUseCase getAllProductsUseCase;
	private final ProductPreviewDTOMapper mapper;

	@Override
	public GetProducts200ResponseDTO getProducts(Integer page, Integer size, String sort, String lang) {
		log.debug("Get all products by language code: {}", lang);

		PageRequest pageRequest = new PageRequest(page, size, SortOrder.fromString(sort));
		PageResponse<Product> productPage = getAllProductsUseCase.getAllProducts(lang, pageRequest);

		List<ProductPreviewDTO> productPreviews = productPage.content().stream().map(mapper::toDto)
				.collect(Collectors.toList());

		GetProducts200ResponseDTO response = new GetProducts200ResponseDTO();
		response.setContent(productPreviews);
		response.setCurrentPage(productPage.currentPage());
		response.setTotalElements((int) productPage.totalElements());
		response.setTotalPages(productPage.totalPages());

		return response;
	}
}
