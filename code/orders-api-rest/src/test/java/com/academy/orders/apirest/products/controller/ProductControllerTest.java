package com.academy.orders.apirest.products.controller;

import com.academy.orders.apirest.common.TestSecurityConfig;
import com.academy.orders.apirest.common.mapper.PageableDTOMapper;
import com.academy.orders.apirest.products.mapper.ProductPreviewDTOMapper;
import com.academy.orders.domain.product.usecase.GetAllProductsUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static com.academy.orders.apirest.ModelUtils.getPageable;
import static com.academy.orders.apirest.ModelUtils.getPageableDTO;
import static com.academy.orders.apirest.ModelUtils.getProduct;
import static com.academy.orders.apirest.ModelUtils.getProductPreviewDTO;
import static com.academy.orders.apirest.ModelUtils.getProductsPage;
import static com.academy.orders.apirest.TestConstants.GET_ALL_PRODUCTS_URL;
import static com.academy.orders.apirest.TestConstants.LANGUAGE_UA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(ProductController.class)
@ContextConfiguration(classes = {ProductController.class})
@Import(value = {ProductPreviewDTOMapper.class, AopAutoConfiguration.class, TestSecurityConfig.class})
class ProductControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GetAllProductsUseCase getAllProductsUseCase;

	@MockBean
	private ProductPreviewDTOMapper productPreviewDTOMapper;

	@MockBean
	private PageableDTOMapper pageableDTOMapper;

	@Test
	void testGetProducts() throws Exception {
		var pageableDTO = getPageableDTO();
		var productPreviewDTO = getProductPreviewDTO();

		var pageable = getPageable();
		var pageProducts = getProductsPage();
		var product = getProduct();

		when(pageableDTOMapper.fromDto(pageableDTO)).thenReturn(pageable);
		when(getAllProductsUseCase.getAllProducts(LANGUAGE_UA, pageable)).thenReturn(pageProducts);
		when(productPreviewDTOMapper.toDto(product)).thenReturn(productPreviewDTO);

		mockMvc.perform(get(GET_ALL_PRODUCTS_URL).param("lang", LANGUAGE_UA).contentType(MediaType.APPLICATION_JSON));

		verify(pageableDTOMapper).fromDto(pageableDTO);
		verify(getAllProductsUseCase).getAllProducts(LANGUAGE_UA, pageable);
		verify(productPreviewDTOMapper).toDto(product);
	}
}
