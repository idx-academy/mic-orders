package com.academy.orders.apirest.products.controller;

import com.academy.orders.apirest.ModelUtils;
import com.academy.orders.apirest.common.TestSecurityConfig;
import com.academy.orders.apirest.common.mapper.PageableDTOMapper;
import com.academy.orders.apirest.products.mapper.PageProductSearchResultDTOMapper;
import com.academy.orders.apirest.products.mapper.ProductDetailsResponseDTOMapper;
import com.academy.orders.apirest.products.mapper.ProductPreviewDTOMapper;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.usecase.GetAllProductsUseCase;
import com.academy.orders.domain.product.usecase.GetProductDetailsByIdUseCase;
import com.academy.orders.domain.product.usecase.GetProductSearchResultsUseCase;
import com.academy.orders_api_rest.generated.model.PageProductSearchResultDTO;
import com.academy.orders_api_rest.generated.model.PageableDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static com.academy.orders.apirest.ModelUtils.getPageOf;
import static com.academy.orders.apirest.ModelUtils.getPageProductsDTO;
import static com.academy.orders.apirest.ModelUtils.getPageable;
import static com.academy.orders.apirest.ModelUtils.getPageableDTO;
import static com.academy.orders.apirest.ModelUtils.getProduct;
import static com.academy.orders.apirest.ModelUtils.getProductDetailsResponseDTO;
import static com.academy.orders.apirest.ModelUtils.getProductsPage;
import static com.academy.orders.apirest.TestConstants.GET_ALL_PRODUCTS_URL;
import static com.academy.orders.apirest.TestConstants.GET_PRODUCT_DETAILS_URL;
import static com.academy.orders.apirest.TestConstants.LANGUAGE_UK;
import static com.academy.orders.apirest.TestConstants.SEARCH_PRODUCTS_URL;
import static com.academy.orders.apirest.TestConstants.TEST_UUID;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductsController.class)
@ContextConfiguration(classes = {ProductsController.class})
@Import(value = {AopAutoConfiguration.class, TestSecurityConfig.class})
class ProductsControllerTest {
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GetAllProductsUseCase getAllProductsUseCase;

	@MockBean
	private GetProductSearchResultsUseCase getProductSearchResultsUseCase;

	@MockBean
	private GetProductDetailsByIdUseCase getProductDetailsByIdUseCase;

	@MockBean
	private ProductPreviewDTOMapper productPreviewDTOMapper;

	@MockBean
	private PageProductSearchResultDTOMapper pageProductSearchResultDTOMapper;

	@MockBean
	private PageableDTOMapper pageableDTOMapper;

	@MockBean
	private ProductDetailsResponseDTOMapper productDetailsResponseDTOMapper;

	@Test
	void getProductsTest() throws Exception {
		var pageableDTO = getPageableDTO();
		var pageable = getPageable();
		var pageProducts = getProductsPage();
		var pageProductsDTO = getPageProductsDTO();
		List<String> tags = emptyList();

		when(pageableDTOMapper.fromDto(pageableDTO)).thenReturn(pageable);
		when(getAllProductsUseCase.getAllProducts(LANGUAGE_UK, pageable, tags)).thenReturn(pageProducts);
		when(productPreviewDTOMapper.toPageProductsDTO(pageProducts)).thenReturn(pageProductsDTO);

		mockMvc.perform(get(GET_ALL_PRODUCTS_URL).param("lang", LANGUAGE_UK).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(pageProductsDTO)));

		verify(pageableDTOMapper).fromDto(pageableDTO);
		verify(getAllProductsUseCase).getAllProducts(LANGUAGE_UK, pageable, emptyList());
		verify(productPreviewDTOMapper).toPageProductsDTO(pageProducts);
	}

	@Test
	@SneakyThrows
	void searchProductsTest() {
		// Given
		String searchQuery = "some text";
		String lang = "en";
		PageableDTO pageableDTO = getPageableDTO();
		Pageable pageable = getPageable();
		Page<Product> productPage = getPageOf(getProduct());
		var expected = ModelUtils.getPageProductSearchResultDTO();

		when(pageableDTOMapper.fromDto(pageableDTO)).thenReturn(pageable);
		when(getProductSearchResultsUseCase.findProductsBySearchQuery(searchQuery, lang, pageable))
				.thenReturn(productPage);
		when(pageProductSearchResultDTOMapper.toDto(productPage)).thenReturn(expected);

		// When
		String response = mockMvc
				.perform(get(SEARCH_PRODUCTS_URL).param("searchQuery", searchQuery).param("lang", lang)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		// Then
		assertEquals(expected, objectMapper.readValue(response, PageProductSearchResultDTO.class));
		verify(pageableDTOMapper).fromDto(pageableDTO);
		verify(getProductSearchResultsUseCase).findProductsBySearchQuery(searchQuery, lang, pageable);
		verify(pageProductSearchResultDTOMapper).toDto(productPage);
	}

	@Test
	@SneakyThrows
	void getProductDetailsByIdTest() {
		var response = getProductDetailsResponseDTO();
		var product = getProduct();
		when(getProductDetailsByIdUseCase.getProductDetailsById(TEST_UUID, "en")).thenReturn(product);
		when(productDetailsResponseDTOMapper.toDTO(product)).thenReturn(response);

		String result = mockMvc.perform(get(GET_PRODUCT_DETAILS_URL, TEST_UUID).param("lang", "en"))
				.andExpect(status().isOk()).andExpect(content().json(objectMapper.writeValueAsString(response)))
				.andReturn().getResponse().getContentAsString();

		assertEquals(objectMapper.writeValueAsString(response), result);
	}
}
