//package com.academy.orders.infrastructure.product.repository;
//
//import com.academy.orders.infrastructure.product.entity.ProductTranslationEntity;
//import com.academy.orders.infrastructure.product.entity.ProductTranslationId;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//
//import java.util.List;
//
//public interface ProductTranslationJpaAdapter extends CrudRepository<ProductTranslationEntity, ProductTranslationId> {
//
//	@Query(value = "select pl from ProductTranslationEntity pl left join fetch pl.product p "
//			+ "left join fetch p.tags pt left join fetch pl.language l where l.code = :language")
//	List<ProductTranslationEntity> getAllProductTranslationByLanguage(String language);
//}
package com.academy.orders.infrastructure.product.repository;

import com.academy.orders.infrastructure.product.entity.ProductTranslationEntity;
import com.academy.orders.infrastructure.product.entity.ProductTranslationId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTranslationJpaAdapter extends CrudRepository<ProductTranslationEntity, ProductTranslationId> {

	@Query(value = "select pl from ProductTranslationEntity pl left join fetch pl.product p "
			+ "left join fetch p.tags pt left join fetch pl.language l where l.code = :language")
	List<ProductTranslationEntity> getAllProductTranslationByLanguage(String language);
}
