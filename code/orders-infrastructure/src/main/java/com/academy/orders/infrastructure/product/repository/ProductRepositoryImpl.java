package com.academy.orders.infrastructure.product.repository;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.repository.ProductRepository;
import com.academy.orders.infrastructure.product.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductRepositoryImpl implements ProductRepository {
	private final ProductJpaAdapter productJpaAdapter;
	private final ProductMapper productMapper;

	@Override
	public Page<Product> getAllProducts(String language, Pageable pageable) {
		log.debug("Fetching all products by language code with pagination and sorting");

		var productEntities = productJpaAdapter.findAllByLanguageCode(language,
				PageRequest.of(pageable.page(), pageable.size()));
		List<Product> products = productMapper.fromEntities(productEntities.getContent());

		return new Page<>(productEntities.getTotalElements(), productEntities.getTotalPages(),
				productEntities.isFirst(), productEntities.isLast(), productEntities.getNumber(),
				productEntities.getNumberOfElements(), productEntities.getSize(), productEntities.isEmpty(), products);
	}
}
