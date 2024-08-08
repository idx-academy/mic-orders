package com.academy.orders.domain.product.usecase;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.product.dto.ProductManagementFilterDto;
import com.academy.orders.domain.product.entity.Product;

/**
 * Use case interface for getting all product information.
 */
public interface GetManagerProductsUseCase {
	/**
	 * Retrieves a paginated list of products for management purposes based on the
	 * provided filter criteria.
	 *
	 * @param pageable
	 *            the {@link Pageable} object for pagination and sorting
	 *            information.
	 * @param productManagementFilter
	 *            the {@link ProductManagementFilterDto} containing filtering
	 *            criteria such as product attributes or status.
	 * @param lang
	 *            the language code for localizing product details.
	 *
	 * @return a {@link Page} of {@link Product} objects that match the specified
	 *         filter criteria.
	 *
	 * @author Denys Ryhal
	 */
	Page<Product> getManagerProducts(Pageable pageable, ProductManagementFilterDto productManagementFilter,
			String lang);
}
