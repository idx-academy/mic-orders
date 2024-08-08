package com.academy.orders.apirest.products.controller;

import com.academy.orders.apirest.common.mapper.PageableDTOMapper;
import com.academy.orders.apirest.products.mapper.ManagementProductMapper;
import com.academy.orders.apirest.products.mapper.ProductRequestDTOMapper;
import com.academy.orders.apirest.products.mapper.ProductResponseDTOMapper;
import com.academy.orders.apirest.products.mapper.ProductStatusDTOMapper;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders.domain.product.usecase.CreateProductUseCase;
import com.academy.orders.domain.product.usecase.GetManagerProductsUseCase;
import com.academy.orders.domain.product.usecase.GetProductByIdUseCase;
import com.academy.orders.domain.product.usecase.UpdateProductUseCase;
import com.academy.orders.domain.product.usecase.UpdateStatusUseCase;
import com.academy.orders_api_rest.generated.api.ProductsManagementApi;
import com.academy.orders_api_rest.generated.model.PageableDTO;
import com.academy.orders_api_rest.generated.model.ProductManagementFilterDTO;
import com.academy.orders_api_rest.generated.model.ProductManagementPageDTO;
import com.academy.orders_api_rest.generated.model.ProductRequestDTO;
import com.academy.orders_api_rest.generated.model.ProductResponseDTO;
import com.academy.orders_api_rest.generated.model.ProductStatusDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductsManagementController implements ProductsManagementApi {
	private final UpdateStatusUseCase updateStatusUseCase;
	private final UpdateProductUseCase updateProductUseCase;
	private final GetManagerProductsUseCase managerProductsUseCase;
	private final CreateProductUseCase createProductUseCase;
	private final GetProductByIdUseCase getProductByIdUseCase;
	private final ProductStatusDTOMapper productStatusDTOMapper;
	private final PageableDTOMapper pageableDTOMapper;
	private final ManagementProductMapper managementProductMapper;
	private final ProductResponseDTOMapper productResponseDTOMapper;
	private final ProductRequestDTOMapper productRequestDTOMapper;

	@Override
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
	public void updateStatus(UUID productId, ProductStatusDTO status) {
		ProductStatus productStatus = productStatusDTOMapper.fromDTO(status);
		updateStatusUseCase.updateStatus(productId, productStatus);
	}

	@Override
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
	public void updateProduct(UUID productId, ProductRequestDTO productRequestDTO) {
		var updateProduct = productRequestDTOMapper.fromDTO(productRequestDTO);
		updateProductUseCase.updateProduct(productId, updateProduct);
	}

	@Override
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
	public ProductManagementPageDTO getProductsForManager(ProductManagementFilterDTO productFilter, String lang,
			PageableDTO pageable) {
		var pageableDomain = pageableDTOMapper.fromDto(pageable);
		var filter = managementProductMapper.fromProductManagementFilterDTO(productFilter);
		var productTranslationPage = managerProductsUseCase.getManagerProducts(pageableDomain, filter, lang);
		return managementProductMapper.fromProductPage(productTranslationPage);
	}

	@Override
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
	public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
		var createProductRequest = productRequestDTOMapper.fromDTO(productRequestDTO);
		Product product = createProductUseCase.createProduct(createProductRequest);
		return productResponseDTOMapper.toDTO(product);
	}

	@Override
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
	public ProductResponseDTO getProductById(UUID productId) {
		var product = getProductByIdUseCase.getProductById(productId);
		return productResponseDTOMapper.toDTO(product);
	}
}
