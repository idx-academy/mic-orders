package com.academy.orders.boot.infrastructure.product.repository;

import com.academy.orders.boot.infrastructure.common.repository.AbstractRepository;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.product.dto.ProductManagementFilterDto;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.ProductTranslation;
import com.academy.orders.domain.product.entity.Tag;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders.domain.product.repository.ProductRepository;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.academy.orders.ModelUtils.getPageable;
import static com.academy.orders.ModelUtils.getPageableSortAsc;
import static com.academy.orders.ModelUtils.getPageableSortDesc;
import static com.academy.orders.ModelUtils.getProductManagement;
import static com.academy.orders.ModelUtils.getProductManagementFilterDto;
import static com.academy.orders.ModelUtils.getProductManagementFilterDtoWithPrices;
import static com.academy.orders.ModelUtils.getProductManagementFilterSearchByName;
import static com.academy.orders.ModelUtils.getProductToSave;
import static com.academy.orders.boot.TestConstants.LANGUAGE_UK;
import static com.academy.orders.boot.TestConstants.NUMBER_OF_TRANSLATIONS_UK_AND_EN;
import static com.academy.orders.boot.TestConstants.PRODUCT_UUID;
import static com.academy.orders.boot.TestConstants.TAG_COMPUTERS;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductRepositoryIT extends AbstractRepository {
	@Autowired
	private ProductRepository productRepository;

	@Test
	void findAllByLanguageWithFilterTest() {
		final var pageable = getPageable();
		final var filter = getProductManagementFilterDto();
		final var actual = productRepository.findAllByLanguageWithFilter(LANGUAGE_UK, filter, pageable);
		final var firstImageLink = actual.content().get(0).image();

		assertContentSchema(actual);
		assertDoesNotThrow(() -> URI.create(firstImageLink));
	}

	@Test
	void findAllByLanguageWithFilterForPriceQuantityAndStatusTest() {
		final var filter = getProductManagementFilterDtoWithPrices();
		final var result = productRepository.findAllByLanguageWithFilter(LANGUAGE_UK, filter, getPageable());

		assertContentSchema(result);
		assertTrue(isFiltered(result, filter));
	}

	@Test
	void findAllByLanguageWithFilterForNameRegexTest() {
		final var pageable = getPageable();
		final var filter = getProductManagementFilterSearchByName();
		final var result = productRepository.findAllByLanguageWithFilter(LANGUAGE_UK, filter, pageable);

		assertContentSchema(result);
		assertTrue(isFilteredByNameRegex(result, filter.searchByName()));
	}

	@Test
	void findAllByLanguageWithFilterForTagsTest() {
		final var pageable = getPageable();
		final var filter = ProductManagementFilterDto.builder().tags(singletonList(TAG_COMPUTERS)).build();
		final var result = productRepository.findAllByLanguageWithFilter(LANGUAGE_UK, filter, pageable);

		assertContentSchema(result);
		assertTrue(isFilteredByTags(result, filter.tags()));
	}

	@Test
	void findAllProductsTest() {
		final var pageable = getPageable();
		final var result = productRepository.findAllProducts(LANGUAGE_UK, pageable, List.of());

		assertContentSchema(result);
		assertTrue(areAllProductsVisible(result));
	}

	@Test
	void findAllProductsForSpecificTagsTest() {
		final var pageable = getPageable();
		final var result = productRepository.findAllProducts(LANGUAGE_UK, pageable, List.of(TAG_COMPUTERS));

		assertContentSchema(result);
		assertTrue(isFilteredByTags(result, List.of(TAG_COMPUTERS)));
		assertTrue(areAllProductsVisible(result));
	}

	@Test
	void findAllProductsSortedByPriceAscTest() {
		final var pageable = getPageableSortAsc();
		final var result = productRepository.findAllProducts(LANGUAGE_UK, pageable, List.of());

		assertTrue(isSortedByPriceAsc(result));
		assertTrue(areAllProductsVisible(result));
	}

	@Test
	void findAllProductsSortedByPriceDescTest() {
		final var pageable = getPageableSortDesc();
		final var result = productRepository.findAllProducts(LANGUAGE_UK, pageable, List.of());

		assertTrue(isSortedByPriceDesc(result));
		assertTrue(areAllProductsVisible(result));
	}

	@Test
	void findAllProductsWithDefaultSortingTest() {
		final var pageable = getPageable();
		final var result = productRepository.findAllProductsWithDefaultSorting(LANGUAGE_UK, pageable, List.of());

		assertContentSchema(result);
	}

	@Test
	void setNewProductQuantityTest() {
		productRepository.setNewProductQuantity(PRODUCT_UUID, 2);
		final var product = productRepository.getById(PRODUCT_UUID);

		assertEquals(2, product.get().quantity());
	}

	@Test
	void existByIdTest() {
		final var result = productRepository.existById(PRODUCT_UUID);
		assertTrue(result);
	}

	@Test
	void updateStatusTest() {
		productRepository.updateStatus(PRODUCT_UUID, ProductStatus.HIDDEN);
		final var product = productRepository.getById(PRODUCT_UUID);
		assertEquals(ProductStatus.HIDDEN, product.get().status());
	}

	@Test
	void findTranslationsByProductId() {
		final var result = productRepository.findTranslationsByProductId(PRODUCT_UUID);

		assertNotNull(result);
		assertEquals(NUMBER_OF_TRANSLATIONS_UK_AND_EN, result.size());
	}

	@Test
	void updateTest() {
		productRepository.update(getProductManagement());
		final var product = productRepository.getById(getProductManagement().id());

		assertEquals(1000, product.get().quantity());
	}

	@Test
	void getByIdTest() {
		final var result = productRepository.getById(PRODUCT_UUID);
		assertNotNull(result);
	}

	@Test
	void saveTest() {
		final var product = productRepository.save(getProductToSave());
		assertNotNull(product);
	}

	@Test
	void searchProductsByNameTest() {
		final var pageable = getPageable();
		final var searchQuery = "Samsung";
		final var result = productRepository.searchProductsByName(searchQuery, LANGUAGE_UK, pageable);

		assertTrue(result.totalElements() > 0);
	}

	@Test
	void getByIdAndLanguageCodeTest() {
		final var result = productRepository.getByIdAndLanguageCode(PRODUCT_UUID, LANGUAGE_UK);
		assertNotNull(result);
	}

	private boolean isFilteredByTags(Page<Product> actual, List<String> tagNames) {
		var products = actual.content();
		var isFiltered = false;
		for (var product : products) {
			isFiltered = product.tags().stream().map(Tag::name).anyMatch(tagNames::contains);
		}
		return isFiltered;
	}

	private void assertContentSchema(Page<Product> actual) {
		var product = actual.content().get(0);
		assertNotNull(actual);
		assertNotNull(actual.content());
		assertEquals(1, product.productTranslations().size());
		assertNotNull(product.tags());
	}

	private boolean isFiltered(Page<Product> actual, ProductManagementFilterDto filter) {
		return actual.content().stream()
				.allMatch(p -> isPricesInBounds(p, filter.priceMore(), filter.priceLess())
						&& isStatusesEqual(p, filter.status())
						&& isQuantityInBounds(p, filter.quantityMore(), filter.quantityLess()));
	}

	private boolean isPricesInBounds(Product product, BigDecimal priceMore, BigDecimal priceLess) {
		var price = product.price();
		return price.compareTo(priceMore) > 0 && price.compareTo(priceLess) < 0;
	}

	private boolean isQuantityInBounds(Product product, Integer priceMore, Integer priceLess) {
		var price = product.quantity();
		return price.compareTo(priceMore) > 0 && price.compareTo(priceLess) < 0;
	}

	private boolean isStatusesEqual(Product product, ProductStatus status) {
		return product.status().equals(status);
	}

	private boolean isFilteredByNameRegex(Page<Product> actual, String nameRegex) {
		return actual.content().stream().flatMap(p -> p.productTranslations().stream()).map(ProductTranslation::name)
				.allMatch(name -> name.toLowerCase().contains(nameRegex));
	}

	private boolean isSortedByPriceAsc(Page<Product> result) {
		var products = result.content();
		for (int i = 0; i < products.size() - 1; i++) {
			if (products.get(i).price().compareTo(products.get(i + 1).price()) > 0) {
				return false;
			}
		}
		return true;
	}

	private boolean isSortedByPriceDesc(Page<Product> result) {
		var products = result.content();
		for (int i = 0; i < products.size() - 1; i++) {
			if (products.get(i).price().compareTo(products.get(i + 1).price()) < 0) {
				return false;
			}
		}
		return true;
	}

	private boolean areAllProductsVisible(Page<Product> result) {
		var products = result.content();
		return products.stream().allMatch(product -> product.status() == ProductStatus.VISIBLE);
	}
}
