package com.academy.orders.apirest.products.controller;

import com.academy.orders.apirest.common.TestSecurityConfig;
import com.academy.orders.apirest.products.ProductController;
import com.academy.orders.apirest.products.mapper.ProductPreviewDTOMapper;
import com.academy.orders.domain.product.usecase.GetAllProductsUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static com.academy.orders.apirest.ModelUtils.getProduct;
import static com.academy.orders.apirest.ModelUtils.getProductPreviewDTO;
import static com.academy.orders.apirest.TestConstants.GET_ALL_PRODUCTS_URL;
import static com.academy.orders.apirest.TestConstants.LANGUAGE_UA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ContextConfiguration(classes = {ProductController.class})
@Import(value = {ProductPreviewDTOMapper.class, AopAutoConfiguration.class, TestSecurityConfig.class})
class ProductControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GetAllProductsUseCase getAllProductsUseCase;

	@MockBean
	private ProductPreviewDTOMapper mapper;

	@Test
	void testGetProducts() throws Exception {
		var productsList = List.of(getProduct());
		var dto = getProductPreviewDTO();
		ObjectMapper objectMapper = new ObjectMapper();
		String expectedContent = objectMapper.writeValueAsString(List.of(dto));

		when(getAllProductsUseCase.getAllProducts(LANGUAGE_UA)).thenReturn(productsList);
		when(mapper.toDto(productsList.get(0))).thenReturn(dto);

		mockMvc.perform(get(GET_ALL_PRODUCTS_URL).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json(expectedContent));

		verify(getAllProductsUseCase).getAllProducts(LANGUAGE_UA);
		verify(mapper).toDto(productsList.get(0));
	}

	@Test
	void testGetProductsEmptyList() throws Exception {
		when(getAllProductsUseCase.getAllProducts(LANGUAGE_UA)).thenReturn(List.of());

		mockMvc.perform(get(GET_ALL_PRODUCTS_URL).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("[]"));

		verify(getAllProductsUseCase).getAllProducts(LANGUAGE_UA);
	}
}
