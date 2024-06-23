package com.academy.orders.apirest.products.controller;

import com.academy.orders.apirest.common.TestSecurityConfig;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ContextConfiguration(classes = {ProductController.class})
@Import(value = {ProductPreviewDTOMapper.class, AopAutoConfiguration.class, TestSecurityConfig.class})
public class ProductControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GetAllProductsUseCase getAllProductsUseCase;

	@MockBean
	private ProductPreviewDTOMapper mapper;

	@Test
	void testGetProducts() throws Exception {
		var productsList = List.of(getProduct());
		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(getProductPreviewDTO());

		when(getAllProductsUseCase.getAllProducts(LANGUAGE_UA)).thenReturn(productsList);
		when(mapper.toDto(productsList.get(0))).thenReturn(getProductPreviewDTO());

		mockMvc.perform(get(GET_ALL_PRODUCTS_URL).contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());

		verify(getAllProductsUseCase).getAllProducts(LANGUAGE_UA);
		verify(mapper).toDto(productsList.get(0));
	}
}
