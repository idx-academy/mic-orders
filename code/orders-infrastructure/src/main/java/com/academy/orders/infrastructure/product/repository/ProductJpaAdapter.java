package com.academy.orders.infrastructure.product.repository;

import com.academy.orders.infrastructure.product.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ProductJpaAdapter extends CrudRepository<ProductEntity, UUID> {

	@Query("SELECT p FROM ProductEntity p JOIN FETCH p.productTranslations pt "
			+ "JOIN FETCH pt.language l LEFT JOIN FETCH p.tags "
			+ "WHERE l.code = :language ORDER BY CASE WHEN :sort = 'name,asc' THEN pt.name END ASC, "
			+ "CASE WHEN :sort = 'name,desc' THEN pt.name END DESC, "
			+ "CASE WHEN :sort = 'price,asc' THEN p.price END ASC, "
			+ "CASE WHEN :sort = 'price,desc' THEN p.price END DESC")
	Page<ProductEntity> findAllByLanguageCode(String language, Pageable pageable, String sort);
}
