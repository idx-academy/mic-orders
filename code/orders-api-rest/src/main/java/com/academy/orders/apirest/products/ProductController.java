package com.academy.orders.apirest.products;

import com.academy.orders.apirest.products.mapper.ProductPreviewDTOMapper;
import com.academy.orders.domain.product.usecase.GetAllProductsUseCase;
import com.academy.orders_api_rest.generated.api.ProductsApi;
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

	private final ProductPreviewDTOMapper mapper;

	@Override
	public List<ProductPreviewDTO> getProducts(String language) {
		log.debug("Get all products by language code: {}", language);
		return getAllProductsUseCase.getAllProducts(language).stream().map(mapper::toDto).toList();
	}
}