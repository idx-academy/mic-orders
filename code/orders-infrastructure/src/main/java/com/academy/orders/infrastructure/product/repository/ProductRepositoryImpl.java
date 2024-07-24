package com.academy.orders.infrastructure.product.repository;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.ProductManagement;
import com.academy.orders.domain.product.entity.ProductTranslationManagement;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders.domain.product.repository.ProductRepository;
import com.academy.orders.infrastructure.product.ProductManagementMapper;
import com.academy.orders.infrastructure.product.ProductMapper;
import java.util.List;
import java.util.UUID;
import com.academy.orders.infrastructure.product.ProductTranslationManagementMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductRepositoryImpl implements ProductRepository {
	private final ProductJpaAdapter productJpaAdapter;
	private final ProductMapper productMapper;
	private final ProductManagementMapper productManagementMapper;
	private final ProductTranslationManagementMapper productTranslationManagementMapper;

	@Override
	public Page<Product> getAllProducts(String language, Pageable pageable) {
		log.debug("Fetching all products by language code with pagination and sorting");
		String sort = String.join(",", pageable.sort());
		var productEntities = productJpaAdapter.findAllByLanguageCodeAndStatusVisible(language,
				PageRequest.of(pageable.page(), pageable.size()), sort);
		List<Product> products = productMapper.fromEntities(productEntities.getContent());

		return new Page<>(productEntities.getTotalElements(), productEntities.getTotalPages(),
				productEntities.isFirst(), productEntities.isLast(), productEntities.getNumber(),
				productEntities.getNumberOfElements(), productEntities.getSize(), productEntities.isEmpty(), products);
	}

	@Override
	public void setNewProductQuantity(UUID productId, Integer quantity) {
		productJpaAdapter.setNewProductQuantity(productId, quantity);
	}

	@Override
	public boolean existById(UUID id) {
		return productJpaAdapter.existsById(id);
	}

	@Override
	public void updateStatus(UUID productId, ProductStatus status) {
		productJpaAdapter.updateProductStatus(productId, status);
	}

	@Override
	public ProductTranslationManagement findByIdAndLanguageCode(UUID id, String languageCode) {
		var productEntity = productJpaAdapter.findByIdAndLanguageCode(id, languageCode);
		return productTranslationManagementMapper.fromEntity(productEntity);
	}

	@Override
	public void update(ProductManagement product) {
		var productEntity = productManagementMapper.toEntity(product);
		productJpaAdapter.save(productEntity);
	}

	@Override
	public Product save(ProductManagement product) {
		var entity = productManagementMapper.toEntity(product);
		return productMapper.fromEntity(productJpaAdapter.save(entity));
	}
}
