package com.academy.orders.infrastructure.product.repository;

import com.academy.orders.infrastructure.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaAdapter extends CrudRepository<ProductEntity, UUID> {
	@Query("SELECT p FROM ProductEntity p LEFT JOIN FETCH p.productTranslations pt LEFT JOIN FETCH pt.language "
			+ "l LEFT JOIN FETCH p.tags t WHERE l.code = :language")
	List<ProductEntity> findAllByLanguageCode(String language);

	@Query("SELECT p FROM ProductEntity p " + "LEFT JOIN FETCH p.productTranslations pt "
			+ "LEFT JOIN FETCH pt.language l " + "LEFT JOIN FETCH p.tags t "
			+ "WHERE p.id in (:productIds) and l.code = :language")
	List<ProductEntity> findAllByIdAndLanguageCode(List<UUID> productIds, String language);
}
