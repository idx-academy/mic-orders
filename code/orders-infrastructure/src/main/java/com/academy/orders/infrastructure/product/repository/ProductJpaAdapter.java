package com.academy.orders.infrastructure.product.repository;

import com.academy.orders.domain.product.dto.ProductManagementFilterDto;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders.infrastructure.product.entity.ProductEntity;
import com.academy.orders.infrastructure.product.entity.ProductTranslationEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaAdapter extends JpaRepository<ProductEntity, UUID> {
	/**
	 * Finds a paginated list of products by language code and with a visible
	 * status, sorted by the specified criteria.
	 *
	 * @param language
	 *            the language code for filtering products.
	 * @param pageable
	 *            the pagination information.
	 * @param sort
	 *            the sort criteria.
	 * @return a {@link Page} containing the filtered list of {@link ProductEntity}
	 *         objects.
	 *
	 * @author Anton Bondar
	 */
	@Query(value = "SELECT p FROM ProductEntity p JOIN FETCH p.productTranslations pt "
			+ "JOIN FETCH pt.language l LEFT JOIN FETCH p.tags t WHERE l.code = :language AND p.status = 'VISIBLE' "
			+ "AND (:#{#tags.isEmpty()} = true OR t.name IN :tags) ORDER BY "
			+ "CASE WHEN :sort = 'name,asc' THEN pt.name END ASC, "
			+ "CASE WHEN :sort = 'name,desc' THEN pt.name END DESC, "
			+ "CASE WHEN :sort = 'createdAt,asc' THEN p.createdAt END ASC, "
			+ "CASE WHEN :sort = 'createdAt,desc' THEN p.createdAt END DESC, "
			+ "CASE WHEN :sort = 'price,asc' THEN p.price END ASC, "
			+ "CASE WHEN :sort = 'price,desc' THEN p.price END DESC", countQuery = "SELECT COUNT(p) FROM ProductEntity p JOIN p.productTranslations pt "
					+ "JOIN pt.language l WHERE l.code = :language AND p.status = 'VISIBLE'")
	Page<ProductEntity> findAllByLanguageCodeAndStatusVisible(String language, Pageable pageable, String sort,
			List<String> tags);

	/**
	 * Finds a list of products by their IDs and language code.
	 *
	 * @param productIds
	 *            the list of product IDs.
	 * @param language
	 *            the language code for filtering products.
	 * @return a {@link List} of {@link ProductEntity} objects.
	 *
	 * @author Denys Liubchenko
	 */
	@Query("SELECT p FROM ProductEntity p LEFT JOIN FETCH p.productTranslations pt "
			+ "LEFT JOIN FETCH pt.language l LEFT JOIN FETCH p.tags t "
			+ "WHERE p.id in (:productIds) and l.code = :language")
	List<ProductEntity> findAllByIdAndLanguageCode(List<UUID> productIds, String language);

	/**
	 * Updates the quantity of a product.
	 *
	 * @param id
	 *            the ID of the product.
	 * @param quantity
	 *            the new quantity to set.
	 *
	 * @author Denys Ryhal
	 */
	@Modifying
	@Query(nativeQuery = true, value = "UPDATE products SET quantity = :quantity WHERE id = :id")
	void setNewProductQuantity(UUID id, Integer quantity);

	/**
	 * Finds paginated product IDs by language code and filter criteria.
	 *
	 * @param lang
	 *            the language code for filtering products.
	 * @param filter
	 *            the filter criteria.
	 * @param pageable
	 *            the pagination information.
	 * @return a {@link Page} containing the filtered list of product IDs.
	 *
	 * @author Denys Ryhal
	 */
	@Query("SELECT p.id FROM ProductEntity p JOIN p.productTranslations pt JOIN pt.language l "
			+ "LEFT JOIN p.tags t WHERE l.code = :lang "
			+ "AND (:#{#filter.status} IS NULL OR p.status = :#{#filter.status})"
			+ "AND (:#{#filter.searchByName} IS NULL OR LOWER(pt.name) LIKE LOWER(CONCAT('%', :#{#filter.searchByName}, '%'))) "
			+ "AND (:#{#filter.quantityLess} IS NULL OR p.quantity <= :#{#filter.quantityLess}) "
			+ "AND (:#{#filter.quantityMore} IS NULL OR p.quantity >= :#{#filter.quantityMore}) "
			+ "AND (:#{#filter.priceLess} IS NULL OR p.price <= :#{#filter.priceLess}) "
			+ "AND (:#{#filter.priceMore} IS NULL OR p.price >= :#{#filter.priceMore}) "
			+ "AND (coalesce(:#{#filter.createdBefore}, NULL) IS NULL OR p.createdAt <= :#{#filter.createdBefore}) "
			+ "AND (coalesce(:#{#filter.createdAfter}, NULL) IS NULL OR p.createdAt >= :#{#filter.createdAfter})"
			+ "AND (:#{#filter.tags.isEmpty()} = true OR t.name IN :#{#filter.tags})")
	Page<UUID> findProductsIdsByLangAndFilters(String lang, @NonNull ProductManagementFilterDto filter,
			Pageable pageable);

	/**
	 * Finds a list of products by their IDs and language code, sorted by the
	 * specified criteria.
	 *
	 * @param lang
	 *            the language code for filtering products.
	 * @param ids
	 *            the list of product IDs.
	 * @param sort
	 *            the sort criteria.
	 * @return a {@link List} of {@link ProductEntity} objects.
	 *
	 * @author Denys Ryhal
	 */
	@Query("SELECT p FROM ProductEntity p JOIN FETCH p.productTranslations pt "
			+ "JOIN FETCH pt.language l LEFT JOIN FETCH p.tags t WHERE (p.id IN :ids) AND pt.language.code = :lang")
	List<ProductEntity> findProductsByIds(String lang, List<UUID> ids, Sort sort);

	/**
	 * Updates the status of a product.
	 *
	 * @param id
	 *            the ID of the product.
	 * @param status
	 *            the new status to set.
	 *
	 * @author Denys Liubchenko
	 */
	@Modifying
	@Query("UPDATE ProductEntity SET status = :status WHERE id = :id")
	void updateProductStatus(UUID id, ProductStatus status);

	/**
	 * Finds a product by its ID and language code.
	 *
	 * @param id
	 *            the ID of the product.
	 * @param languageCode
	 *            the language code for filtering the product translation.
	 * @return the {@link ProductEntity} object.
	 *
	 * @author Anton Bondar
	 */
	@Query("SELECT p FROM ProductEntity p LEFT JOIN FETCH p.productTranslations pt "
			+ "LEFT JOIN FETCH pt.language l LEFT JOIN FETCH p.tags t WHERE p.id = :id AND l.code = :languageCode")
	ProductEntity findProductByIdAndLanguageCode(UUID id, String languageCode);

	/**
	 * Finds a product translation by product ID and language code.
	 *
	 * @param id
	 *            the ID of the product.
	 * @param languageCode
	 *            the language code for filtering the product translation.
	 * @return the {@link ProductTranslationEntity} object.
	 *
	 * @author Anton Bondar
	 */
	@Query("SELECT pt FROM ProductTranslationEntity pt LEFT JOIN FETCH pt.product p "
			+ "LEFT JOIN FETCH pt.language l LEFT JOIN FETCH p.tags t WHERE p.id = :id AND l.code = :languageCode")
	ProductTranslationEntity findTranslationByIdAndLanguageCode(UUID id, String languageCode);

	/**
	 * Retrieves a paginated list of ProductTranslationEntity objects based on a
	 * search query and language code.
	 *
	 * @param searchQuery
	 *            the search query to filter product names
	 * @param lang
	 *            the language code to filter product translations
	 * @param pageable
	 *            the pagination information
	 *
	 * @return a paginated list of ProductTranslationEntity objects that match the
	 *         search criteria
	 */
	@Query("SELECT pt FROM ProductTranslationEntity pt LEFT JOIN FETCH pt.product p LEFT JOIN pt.language l "
			+ "WHERE l.code = :lang AND LOWER(pt.name) LIKE LOWER(CONCAT('%', :searchQuery, '%'))")
	PageImpl<ProductTranslationEntity> findProductsByNameWithSearchQuery(String searchQuery, String lang,
			PageRequest pageable);
}
