package com.academy.orders.infrastructure.product.repository;

import com.academy.orders.infrastructure.product.entity.ProductEntity;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaAdapter extends CrudRepository<ProductEntity, UUID> {
	@NonNull
	List<ProductEntity> findAll();
}
