package com.academy.orders.infrastructure.product.repository;

import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.repository.ProductRepository;
import com.academy.orders.infrastructure.product.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductRepositoryImpl implements ProductRepository {
	private final ProductJpaAdapter productJpaAdapter;
	private final ProductMapper productMapper;

	@Override
	public List<Product> getAllProducts(String language) {
		log.debug("Fetching all products by language code");
		return productMapper.fromEntities(productJpaAdapter.findAllByLanguageCode(language));
	}
}
