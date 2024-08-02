package com.academy.orders.domain.product.usecase;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.product.entity.Product;

public interface GetProductSearchResultsUseCase {
	/**
	 * Retrieves a paginated list of Product objects based on a search query and
	 * language code.
	 *
	 * @param searchQuery
	 *            the search query to filter product names
	 * @param lang
	 *            the language code to filter products
	 * @param pageable
	 *            the pagination information
	 *
	 * @return a paginated list of Product objects that match the search criteria
	 *
	 * @author Denys Liubchenko
	 */
	Page<Product> findProductsBySearchQuery(String searchQuery, String lang, Pageable pageable);
}
