package com.academy.orders.domain.product.usecase;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.product.entity.Product;
import java.util.List;

public interface GetAllProductsUseCase {
	/**
	 * Retrieves a paginated list of products based on the provided language,
	 * pagination details, and tags.
	 *
	 * @param language
	 *            the language code for localizing product details.
	 * @param pageable
	 *            the {@link Pageable} object for pagination and sorting
	 *            information.
	 * @param tags
	 *            a {@link List} of tags to filter the products by. Can be empty to
	 *            retrieve products without tag filtering.
	 * @return a {@link Page} of {@link Product} objects that match the specified
	 *         criteria.
	 *
	 *
	 * @author Anton Bodnar, Yurii Osovskyi, Denys Ryhal
	 */

	Page<Product> getAllProducts(String language, Pageable pageable, List<String> tags);
}
