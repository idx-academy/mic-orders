package com.academy.orders.domain.product.repository;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.product.dto.ProductManagementFilterDto;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.ProductManagement;
import com.academy.orders.domain.product.entity.ProductTranslationManagement;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.lang.NonNull;

public interface ProductRepository {
	/**
	 * Retrieves a paginated list of products based on the provided language and
	 * pageable information.
	 *
	 * @param language
	 *            the language code to filter the products.
	 * @param pageable
	 *            the pageable information for pagination and sorting.
	 * @param tags
	 *            list of tag's names for filtering.
	 * @return a page containing the products that match the criteria.
	 *
	 * @author Anton Bondar, Yurii Osovskyi
	 */
	Page<Product> findAllProducts(String language, Pageable pageable, List<String> tags);

	/**
	 * Method sets new quantity of products.
	 *
	 * @param productId
	 *            id of the product
	 * @param quantity
	 *            new quantity of the product
	 *
	 * @author Denys Ryhal
	 */
	void setNewProductQuantity(UUID productId, Integer quantity);

	/**
	 * Method checks if product with id already exists.
	 *
	 * @param id
	 *            id of the product
	 *
	 * @return {@link Boolean}
	 * @author Denys Ryhal
	 */
	boolean existById(UUID id);

	/**
	 * Updates the status of a product.
	 *
	 * @param productId
	 *            The unique identifier of the product whose status will be updated.
	 * @param status
	 *            The new status for the product, represented by
	 *            {@link ProductStatus}.
	 * @author Denys Liubchenko
	 */
	void updateStatus(UUID productId, ProductStatus status);

	/**
	 * Retrieves a list of {@link ProductTranslationManagement} entities by the
	 * unique identifier of the product.
	 *
	 * @param productId
	 *            the unique identifier of the product.
	 * @return a set of {@link ProductTranslationManagement} entities.
	 * @author Anton Bondar
	 */
	Set<ProductTranslationManagement> findTranslationsByProductId(UUID productId);

	/**
	 * Saves a new product using the provided product creation request data transfer
	 * object (DTO). Returns the saved product entity.
	 *
	 * @param product
	 *            the DTO containing the information necessary to create a new
	 *            product
	 * @return the saved product entity
	 * @author Yurii Osovskyi
	 */
	Product save(ProductManagement product);

	/**
	 * Update an {@link ProductManagement} entity.
	 *
	 * @param product
	 *            the {@link ProductManagement} entity to be updated.
	 * @author Anton Bondar
	 */
	void update(ProductManagement product);

	/**
	 * Retrieves a paginated list of products filtered by language and additional
	 * criteria.
	 *
	 * @param language
	 *            the language filter to apply to the products.
	 * @param filter
	 *            the additional criteria to filter the products.
	 * @param pageable
	 *            the pagination information.
	 * @return a {@link Page} containing the filtered list of {@link Product}
	 *         objects.
	 *
	 * @author Denys Ryhal
	 */
	Page<Product> findAllByLanguageWithFilter(String language, @NonNull ProductManagementFilterDto filter,
			Pageable pageable);

	/**
	 * Retrieves a {@link Product} entity by its unique identifier.
	 *
	 * @param productId
	 *            the unique identifier of the product.
	 * @return the {@link Product} entity.
	 *
	 * @author Anton Bondar
	 */
	Optional<Product> getById(UUID productId);

	/**
	 * Searches for products by their name based on the given search query and
	 * language.
	 *
	 * @param searchQuery
	 *            the search term used to find products by name.
	 * @param lang
	 *            the language code for localizing the product names.
	 * @param pageable
	 *            the pagination information, specifying the page number, size, and
	 *            sorting criteria.
	 *
	 * @return a {@code Page} of {@code Product} objects that match the search
	 *         criteria.
	 *
	 * @author Denys Liubchenko
	 */
	Page<Product> searchProductsByName(String searchQuery, String lang, Pageable pageable);

	/**
	 * Retrieves an optional product based on its ID and language code.
	 *
	 * @param productId
	 *            the unique identifier of the product
	 * @param lang
	 *            the language code to filter the product translation
	 * @return an {@link Optional} containing the product if found, otherwise an
	 *         empty {@link Optional}
	 */
	Optional<Product> getByIdAndLanguageCode(UUID productId, String lang);
}
