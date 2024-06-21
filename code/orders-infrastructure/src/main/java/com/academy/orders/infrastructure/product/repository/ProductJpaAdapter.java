package com.academy.orders.infrastructure.product.repository;

import com.academy.orders.infrastructure.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaAdapter extends CrudRepository<ProductEntity, UUID> {
	@Query("SELECT p FROM ProductEntity p JOIN FETCH p.productTranslations pt JOIN FETCH pt.language " +
			"l LEFT JOIN FETCH p.tags t WHERE l.code = :language")
	List<ProductEntity> findAllByLanguageCode(String language);
}
