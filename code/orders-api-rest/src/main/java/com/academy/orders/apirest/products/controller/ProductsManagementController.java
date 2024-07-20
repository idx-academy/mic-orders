package com.academy.orders.apirest.products.controller;

import com.academy.orders.apirest.products.mapper.ProductStatusDTOMapper;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders.domain.product.usecase.UpdateStatusUseCase;
import com.academy.orders_api_rest.generated.api.ProductsManagementApi;
import com.academy.orders_api_rest.generated.model.ProductStatusDTO;
import java.util.UUID;
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
	private final ProductStatusDTOMapper productStatusDTOMapper;

	@PreAuthorize("hasAnyAuthority('ROLE_MANAGER')")
	public void updateStatus(UUID productId, ProductStatusDTO status) {
		ProductStatus productStatus = productStatusDTOMapper.fromDTO(status);
		updateStatusUseCase.updateStatus(productId, productStatus);
	}
}
