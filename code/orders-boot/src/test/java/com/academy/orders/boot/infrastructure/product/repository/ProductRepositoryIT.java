package com.academy.orders.boot.infrastructure.product.repository;

import com.academy.orders.boot.infrastructure.common.AbstractRepository;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.product.dto.ProductManagementFilterDto;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.ProductTranslation;
import com.academy.orders.domain.product.entity.Tag;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders.domain.product.repository.ProductRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.academy.orders.domain.product.entity.enumerated.ProductStatus.VISIBLE;
import static java.math.BigDecimal.valueOf;
import static java.time.LocalDateTime.of;
import static java.time.Month.JULY;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductRepositoryIT extends AbstractRepository {
	private final Pageable pageable = new Pageable(0, 10, emptyList());
	private final String lang = "uk";

	@Autowired
	private ProductRepository productRepository;

	@Test
	void findAllByLanguageWithFilterTest() {
		final var totalElements = 34;
		final var filter = ProductManagementFilterDto.builder().tags(emptyList()).build();

		final var actual = productRepository.findAllByLanguageWithFilter(lang, filter, pageable);

		assertContentSchema(actual);
		assertEquals(totalElements, actual.totalElements());
	}

	@Test
	void findAllByLanguageWithFilterForPriceCreatedAtQuantityAndStatusTest() {
		final var totalElements = 4;
		final var filter = ProductManagementFilterDto.builder().priceMore(valueOf(1000)).priceLess(valueOf(1500))
				.createdAfter(of(2024, JULY, 20, 13, 10)).createdBefore(of(2024, JULY, 21, 11, 16)).quantityMore(5)
				.quantityLess(11).status(VISIBLE).tags(emptyList()).build();

		final var result = productRepository.findAllByLanguageWithFilter(lang, filter, pageable);

		assertContentSchema(result);
		assertEquals(totalElements, result.totalElements());
		assertTrue(isFiltered(result, filter));
	}

	private boolean isFiltered(Page<Product> actual, ProductManagementFilterDto filter) {
		return actual.content().stream().allMatch(p -> isDatesInBounds(p, filter.createdAfter(), filter.createdBefore())
				&& isPricesInBounds(p, filter.priceMore(), filter.priceLess()) && isStatusesEqual(p, filter.status())
				&& isQuantityInBounds(p, filter.quantityMore(), filter.quantityLess()));
	}

	private boolean isDatesInBounds(Product product, LocalDateTime createdAfter, LocalDateTime createdBefore) {
		var date = product.createdAt();
		return date.isAfter(createdAfter) && date.isBefore(createdBefore);
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

	@Test
	void findAllByLanguageWithFilterForNameRegexTest() {
		final var totalElements = 5;
		final var filter = ProductManagementFilterDto.builder().searchByName("iphone").tags(emptyList()).build();

		final var result = productRepository.findAllByLanguageWithFilter(lang, filter, pageable);

		assertContentSchema(result);
		assertEquals(totalElements, result.totalElements());
		assertTrue(isFilteredByNameRegex(result, filter.searchByName()));
	}

	private boolean isFilteredByNameRegex(Page<Product> actual, String nameRegex) {
		return actual.content().stream().flatMap(p -> p.productTranslations().stream()).map(ProductTranslation::name)
				.allMatch(name -> name.toLowerCase().contains(nameRegex));
	}

	@Test
	void findAllByLanguageWithFilterForTagsTest() {
		final var totalElements = 16;
		final var filter = ProductManagementFilterDto.builder().tags(singletonList("category:computer")).build();

		final var result = productRepository.findAllByLanguageWithFilter(lang, filter, pageable);

		assertContentSchema(result);
		assertEquals(totalElements, result.totalElements());
		assertTrue(isFilteredByTags(result, filter.tags()));
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

}
