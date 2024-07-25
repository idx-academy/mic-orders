package com.academy.orders.apirest.products.controller;

import com.academy.orders.apirest.products.mapper.UpdateProductRequestDTOMapper;
import com.academy.orders.apirest.common.mapper.PageableDTOMapper;
import com.academy.orders.apirest.products.mapper.ManagementProductMapper;
import com.academy.orders.apirest.products.mapper.CreateProductRequestDTOMapper;
import com.academy.orders.apirest.products.mapper.ProductResponseDTOMapper;
import com.academy.orders.apirest.products.mapper.ProductStatusDTOMapper;
import com.academy.orders.domain.product.usecase.CreateProductUseCase;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders.domain.product.usecase.UpdateProductUseCase;
import com.academy.orders.domain.product.usecase.GetManagerProductsUseCase;
import com.academy.orders.domain.product.usecase.UpdateStatusUseCase;
import com.academy.orders_api_rest.generated.api.ProductsManagementApi;
import com.academy.orders_api_rest.generated.model.CreateProductRequestDTO;
import com.academy.orders_api_rest.generated.model.ProductResponseDTO;
import com.academy.orders_api_rest.generated.model.ProductStatusDTO;
import java.util.UUID;
import com.academy.orders_api_rest.generated.model.UpdateProductRequestDTO;
import com.academy.orders_api_rest.generated.model.PageableDTO;
import com.academy.orders_api_rest.generated.model.ProductManagementFilterDTO;
import com.academy.orders_api_rest.generated.model.ProductManagementPageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class ProductsManagementController implements ProductsManagementApi {
	private final UpdateStatusUseCase updateStatusUseCase;
	private final UpdateProductUseCase updateProductUseCase;
	private final ProductStatusDTOMapper productStatusDTOMapper;
	private final UpdateProductRequestDTOMapper updateProductRequestDTOMapper;
	private final PageableDTOMapper pageableDTOMapper;
	private final ManagementProductMapper managementProductMapper;
	private final GetManagerProductsUseCase managerProductsUseCase;
	private final CreateProductUseCase createProductUseCase;
	private final ProductResponseDTOMapper productResponseDTOMapper;
	private final CreateProductRequestDTOMapper createProductRequestDTOMapper;

	@Override
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGER')")
	public void updateStatus(UUID productId, ProductStatusDTO status) {
		ProductStatus productStatus = productStatusDTOMapper.fromDTO(status);
		updateStatusUseCase.updateStatus(productId, productStatus);
	}

	@Override
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGER')")
	public void updateProduct(UUID productId, String lang, UpdateProductRequestDTO updateProductRequestDTO) {
		var updateProduct = updateProductRequestDTOMapper.fromDTO(updateProductRequestDTO);
		updateProductUseCase.updateProduct(productId, lang, updateProduct);
	}

	@Override
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGER')")
	public ProductManagementPageDTO getProductsForManager(ProductManagementFilterDTO productFilter, String lang,
			PageableDTO pageable) {
		var pageableDomain = pageableDTOMapper.fromDto(pageable);
		var filter = managementProductMapper.fromProductManagementFilterDTO(productFilter);
		var productTranslationPage = managerProductsUseCase.getManagerProducts(pageableDomain, filter, lang);
		return managementProductMapper.fromProductPage(productTranslationPage);
	}

	@Override
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGER')")
	public ProductResponseDTO createProduct(CreateProductRequestDTO createProductRequestDTO) {
		var createProductRequest = createProductRequestDTOMapper.fromDTO(createProductRequestDTO);
		Product product = createProductUseCase.createProduct(createProductRequest);
		return productResponseDTOMapper.toDTO(product);
	}
}
