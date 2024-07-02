package com.academy.orders.infrastructure.product.repository;

import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.repository.ProductRepository;
import com.academy.orders.infrastructure.product.ProductMapper;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

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

	@Override
	public void setNewProductQuantity(UUID productId, Integer quantity) {
		productJpaAdapter.setNewProductQuantity(productId, quantity);
	}

	@Override
	public boolean existById(UUID id) {
		return productJpaAdapter.existsById(id);
	}
}
