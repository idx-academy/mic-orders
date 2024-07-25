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
import static com.academy.orders.apirest.TestConstants.LANGUAGE_UK;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductsController.class)
@ContextConfiguration(classes = {ProductsController.class})
@Import(value = {AopAutoConfiguration.class, TestSecurityConfig.class})
class ProductsControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GetAllProductsUseCase getAllProductsUseCase;

	@MockBean
	private ProductPreviewDTOMapper productPreviewDTOMapper;

	@MockBean
	private PageableDTOMapper pageableDTOMapper;

	@Test
	void getProductsTest() throws Exception {
		var pageableDTO = getPageableDTO();
		var productPreviewDTO = getProductPreviewDTO();

		var pageable = getPageable();
		var pageProducts = getProductsPage();
		var product = getProduct();

		when(pageableDTOMapper.fromDto(pageableDTO)).thenReturn(pageable);
		when(getAllProductsUseCase.getAllProducts(LANGUAGE_UK, pageable)).thenReturn(pageProducts);
		when(productPreviewDTOMapper.toDto(product)).thenReturn(productPreviewDTO);

		mockMvc.perform(get(GET_ALL_PRODUCTS_URL).param("lang", LANGUAGE_UK).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.content[0].id", is(productPreviewDTO.getId())))
				.andExpect(jsonPath("$.content[0].name", is(productPreviewDTO.getName())))
				.andExpect(jsonPath("$.totalElements", is(1)))
				.andExpect(jsonPath("$.totalPages", is(pageProducts.totalPages())))
				.andExpect(jsonPath("$.first", is(pageProducts.first())))
				.andExpect(jsonPath("$.last", is(pageProducts.last())))
				.andExpect(jsonPath("$.number", is(pageProducts.number())))
				.andExpect(jsonPath("$.numberOfElements", is(pageProducts.numberOfElements())))
				.andExpect(jsonPath("$.size", is(pageProducts.size())))
				.andExpect(jsonPath("$.empty", is(pageProducts.empty())));

		verify(pageableDTOMapper).fromDto(pageableDTO);
		verify(getAllProductsUseCase).getAllProducts(LANGUAGE_UK, pageable);
		verify(productPreviewDTOMapper).toDto(product);
	}
}
