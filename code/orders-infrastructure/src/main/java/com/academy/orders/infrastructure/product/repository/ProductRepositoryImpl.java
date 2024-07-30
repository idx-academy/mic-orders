package com.academy.orders.infrastructure.product.repository;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.common.respository.ImageRepository;
import com.academy.orders.domain.product.dto.ProductManagementFilterDto;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.ProductManagement;
import com.academy.orders.domain.product.entity.ProductTranslationManagement;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders.domain.product.repository.ProductRepository;
import com.academy.orders.infrastructure.common.PageableMapper;
import com.academy.orders.infrastructure.product.ProductManagementMapper;
import com.academy.orders.infrastructure.product.ProductMapper;
import com.academy.orders.infrastructure.product.ProductPageMapper;
import com.academy.orders.infrastructure.product.ProductTranslationManagementMapper;
import com.academy.orders.infrastructure.product.entity.ProductEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProductRepositoryImpl implements ProductRepository {
	private final ProductJpaAdapter productJpaAdapter;
	private final ProductMapper productMapper;
	private final ProductManagementMapper productManagementMapper;
	private final ProductTranslationManagementMapper productTranslationManagementMapper;
	private final ProductPageMapper productPageMapper;
	private final PageableMapper pageableMapper;
	private final ImageRepository imageRepository;

	@Override
	public Page<Product> getAllProducts(String language, Pageable pageable) {
		log.debug("Fetching all products by language code with pagination and sorting");
		String sort = String.join(",", pageable.sort());
		var productEntities = productJpaAdapter.findAllByLanguageCodeAndStatusVisible(language,
				PageRequest.of(pageable.page(), pageable.size()), sort);
		setImageNames(productEntities.getContent());

		List<Product> products = productMapper.fromEntities(productEntities.getContent());
		return new Page<>(productEntities.getTotalElements(), productEntities.getTotalPages(),
				productEntities.isFirst(), productEntities.isLast(), productEntities.getNumber(),
				productEntities.getNumberOfElements(), productEntities.getSize(), productEntities.isEmpty(), products);
	}

	private void setImageNames(List<ProductEntity> products) {
		products.forEach(p -> {
			var name = p.getImage().substring(p.getImage().lastIndexOf("/") + 1);
			p.setImage(name);
		});
	}

	@Override
	@Transactional
	public void setNewProductQuantity(UUID productId, Integer quantity) {
		productJpaAdapter.setNewProductQuantity(productId, quantity);
	}

	@Override
	public boolean existById(UUID id) {
		return productJpaAdapter.existsById(id);
	}

	@Override
	@Transactional
	public void updateStatus(UUID productId, ProductStatus status) {
		productJpaAdapter.updateProductStatus(productId, status);
	}

	@Override
	public ProductManagement findProductByIdAndLanguageCode(UUID productId, String languageCode) {
		var productEntity = productJpaAdapter.findProductByIdAndLanguageCode(productId, languageCode);
		return productManagementMapper.fromEntity(productEntity);
	}

	@Override
	public ProductTranslationManagement findTranslationByIdAndLanguageCode(UUID id, String languageCode) {
		var productTranslationEntity = productJpaAdapter.findTranslationByIdAndLanguageCode(id, languageCode);
		return productTranslationManagementMapper.fromEntity(productTranslationEntity);
	}

	@Override
	@Transactional
	public void update(ProductManagement product) {
		var productEntity = productManagementMapper.toEntity(product);
		productJpaAdapter.save(productEntity);
	}

	@Override
	public Page<Product> findAllByLanguageWithFilter(String lang, @NonNull ProductManagementFilterDto filter,
			Pageable pageableDomain) {
		var pageable = pageableMapper.fromDomain(pageableDomain);
		var ids = productJpaAdapter.findProductsIdsByLangAndFilters(lang, filter, pageable);
		var products = productJpaAdapter.findProductsByIds(lang, ids.getContent(), pageable.getSort());

		return productPageMapper.toDomain(new PageImpl<>(products, pageable, ids.getTotalElements()));
	}

	@Override
	public Optional<Product> getById(UUID productId) {
		var productEntity = productJpaAdapter.findById(productId);
		return productEntity.map(productMapper::fromEntity);
	}

	@Override
	public Product save(ProductManagement product) {
		var entity = productManagementMapper.toEntity(product);
		return productMapper.fromEntity(productJpaAdapter.save(entity));
	}

}
