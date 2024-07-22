package com.academy.orders.infrastructure.product.repository;

import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders.infrastructure.product.entity.ProductEntity;
import java.util.List;
import java.util.UUID;
import com.academy.orders.infrastructure.product.entity.ProductTranslationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaAdapter extends JpaRepository<ProductEntity, UUID> {
	@Query(value = "SELECT p FROM ProductEntity p JOIN FETCH p.productTranslations pt "
			+ "JOIN FETCH pt.language l LEFT JOIN FETCH p.tags WHERE l.code = :language ORDER BY "
			+ "CASE WHEN :sort = 'name,asc' THEN pt.name END ASC, "
			+ "CASE WHEN :sort = 'name,desc' THEN pt.name END DESC, "
			+ "CASE WHEN :sort = 'createdAt,asc' THEN p.createdAt END ASC, "
			+ "CASE WHEN :sort = 'createdAt,desc' THEN p.createdAt END DESC, "
			+ "CASE WHEN :sort = 'price,asc' THEN p.price END ASC, "
			+ "CASE WHEN :sort = 'price,desc' THEN p.price END DESC", countQuery = "SELECT COUNT(p) "
					+ "FROM ProductEntity p JOIN p.productTranslations pt "
					+ "JOIN pt.language l WHERE l.code = :language AND p.status = 'VISIBLE'")
	Page<ProductEntity> findAllByLanguageCodeAndStatusVisible(String language, Pageable pageable, String sort);

	@Query("SELECT p FROM ProductEntity p LEFT JOIN FETCH p.productTranslations pt "
			+ "LEFT JOIN FETCH pt.language l LEFT JOIN FETCH p.tags t "
			+ "WHERE p.id in (:productIds) and l.code = :language")
	List<ProductEntity> findAllByIdAndLanguageCode(List<UUID> productIds, String language);

	@Modifying
	@Query(nativeQuery = true, value = "UPDATE products SET quantity = :quantity WHERE id = :id")
	void setNewProductQuantity(UUID id, Integer quantity);

	@Modifying
	@Query(nativeQuery = true, value = "UPDATE products SET status = :status WHERE id = :id")
	void updateProductStatus(UUID id, ProductStatus status);

	@Query("SELECT pt FROM ProductTranslationEntity pt LEFT JOIN FETCH pt.product p "
			+ "LEFT JOIN FETCH pt.language l LEFT JOIN FETCH p.tags t WHERE p.id = :id AND l.code = :languageCode")
	ProductTranslationEntity findByIdAndLanguageCode(UUID id, String languageCode);
}
