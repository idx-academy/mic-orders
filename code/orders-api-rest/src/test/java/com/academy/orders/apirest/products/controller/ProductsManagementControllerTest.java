package com.academy.orders.apirest.products.controller;

import com.academy.orders.apirest.common.TestSecurityConfig;
import com.academy.orders.apirest.products.mapper.ProductStatusDTOMapper;
import com.academy.orders.apirest.products.mapper.UpdateProductRequestDTOMapper;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders.domain.product.usecase.UpdateProductUseCase;
import com.academy.orders.domain.product.usecase.UpdateStatusUseCase;
import com.academy.orders_api_rest.generated.model.ProductStatusDTO;
import java.util.UUID;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static com.academy.orders.apirest.TestConstants.TEST_UUID;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductsManagementController.class)
@ContextConfiguration(classes = {ProductsManagementController.class})
@Import(value = {AopAutoConfiguration.class, TestSecurityConfig.class})
class ProductsManagementControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UpdateStatusUseCase updateStatusUseCase;

	@MockBean
	private UpdateProductUseCase updateProductUseCase;

	@MockBean
	private ProductStatusDTOMapper productStatusDTOMapper;

	@MockBean
	private UpdateProductRequestDTOMapper updateProductRequestDTOMapper;

	@Test
	@WithMockUser(authorities = {"ROLE_MANAGER"})
	@SneakyThrows
	void updateStatusTest() {
		// Given
		UUID productId = TEST_UUID;
		ProductStatusDTO productStatusDTO = ProductStatusDTO.VISIBLE;
		ProductStatus productStatus = ProductStatus.VISIBLE;

		when(productStatusDTOMapper.fromDTO(productStatusDTO)).thenReturn(productStatus);
		doNothing().when(updateStatusUseCase).updateStatus(productId, productStatus);

		// When
		mockMvc.perform(patch("/v1/management/products/{productId}/status", productId)
				.param("status", productStatusDTO.getValue()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

		// Then
		verify(productStatusDTOMapper).fromDTO(productStatusDTO);
		verify(updateStatusUseCase).updateStatus(productId, productStatus);
	}
}
