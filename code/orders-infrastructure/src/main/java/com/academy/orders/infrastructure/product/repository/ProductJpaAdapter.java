package com.academy.orders.infrastructure.product.repository;

import com.academy.orders.infrastructure.product.entity.ProductEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductJpaAdapter extends CrudRepository<ProductEntity, UUID> {
	@Query("SELECT p FROM ProductEntity p JOIN FETCH p.productTranslations pt JOIN FETCH pt.language "
			+ "l LEFT JOIN FETCH p.tags t WHERE l.code = :language")
	List<ProductEntity> findAllByLanguageCode(String language);
	
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE products SET quantity = :quantity WHERE id = :id")
	void setNewProductQuantity(UUID id, Integer quantity);

}
