package com.academy.orders.infrastructure.product.repository;

import com.academy.orders.infrastructure.product.entity.ProductEntity;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
					+ "JOIN pt.language l WHERE l.code = :language")
	Page<ProductEntity> findAllByLanguageCode(String language, Pageable pageable, String sort);

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE products SET quantity = :quantity WHERE id = :id")
	void setNewProductQuantity(UUID id, Integer quantity);

}
