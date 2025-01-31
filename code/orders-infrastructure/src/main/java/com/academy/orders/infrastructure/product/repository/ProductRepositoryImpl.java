package com.academy.orders.infrastructure.product.repository;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.discount.entity.Discount;
import com.academy.orders.domain.product.dto.ProductManagementFilterDto;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.ProductManagement;
import com.academy.orders.domain.product.entity.ProductTranslationManagement;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders.domain.product.repository.ProductRepository;
import com.academy.orders.infrastructure.common.PageableMapper;
import com.academy.orders.infrastructure.discount.DiscountMapper;
import com.academy.orders.infrastructure.product.ProductManagementMapper;
import com.academy.orders.infrastructure.product.ProductMapper;
import com.academy.orders.infrastructure.product.ProductPageMapper;
import com.academy.orders.infrastructure.product.ProductTranslationManagementMapper;
import com.academy.orders.infrastructure.product.entity.ProductEntity;
import com.academy.orders.infrastructure.product.entity.ProductTranslationEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;

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
	private final DiscountMapper discountMapper;
	private final PageableMapper pageableMapper;

	@Override
	public Page<Product> findAllProducts(String language, Pageable pageable, List<String> tags) {
		List<String> tagList = isNull(tags) ? emptyList() : tags;
		var pageableSpring = pageableMapper.fromDomain(pageable);
		var translations = productJpaAdapter.findAllByLanguageCodeAndStatusVisible(language, pageableSpring, tagList);
		List<ProductEntity> products = translations.getContent().stream().map(ProductTranslationEntity::getProduct)
				.toList();
		productJpaAdapter.findAllByIdAndLanguageCode(products.stream().map(ProductEntity::getId).toList(), language);

		return productPageMapper.fromProductTranslationEntity(translations);
	}

	@Override
	public Page<Product> findProductsWhereDiscountIsNotNull(String language, Pageable pageable) {
		var pageableSpring = pageableMapper.fromDomain(pageable);
		var translations = productJpaAdapter.findAllByLanguageCodeAndStatusVisibleAndDiscountNotNull(language,
				pageableSpring);
		return productPageMapper.fromProductTranslationEntity(translations);
	}

	@Override
	public Page<Product> findAllProductsWithDefaultSorting(String language, Pageable pageable, List<String> tags) {
		List<String> tagList = isNull(tags) ? emptyList() : tags;
		var pageableSpring = pageableMapper.fromDomain(pageable);
		var productEntities = productJpaAdapter.findAllByLanguageCodeAndStatusVisibleOrderedByDefault(language,
				pageableSpring, tagList);
		productJpaAdapter.findAllByIdAndLanguageCode(
				productEntities.getContent().stream().map(ProductEntity::getId).toList(), language);

		return productPageMapper.toDomain(productEntities);
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
	public Set<ProductTranslationManagement> findTranslationsByProductId(UUID id) {
		var productTranslationEntity = productJpaAdapter.findTranslationsByProductId(id);
		return productTranslationManagementMapper.fromEntities(productTranslationEntity);
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
		var productEntity = productJpaAdapter.findProductByProductId(productId);
		return productEntity.map(productMapper::fromEntity);
	}

	@Override
	public Product save(ProductManagement product) {
		var entity = productManagementMapper.toEntity(product);
		return productMapper.fromEntity(productJpaAdapter.save(entity));
	}

	@Override
	public Page<Product> searchProductsByName(String searchQuery, String lang, Pageable pageableDomain) {
		var pageable = pageableMapper.fromDomain(pageableDomain);
		var translations = productJpaAdapter.findProductsByNameWithSearchQuery(searchQuery, lang, pageable);
		return productPageMapper.fromProductTranslationEntity(translations);
	}

	@Override
	public Optional<Product> getByIdAndLanguageCode(UUID productId, String lang) {
		var productEntity = productJpaAdapter.findProductByProductIdAndLanguageCode(productId, lang);
		return productEntity.map(productMapper::fromEntity);
	}

	@Override
	public boolean updateProductDiscount(UUID productId, Discount discount) {
		var discountEntity = discountMapper.toEntity(discount);
		var productEntity = productJpaAdapter.findProductByProductId(productId);
		if (productEntity.isPresent()) {
			productEntity.get().setDiscount(discountEntity);
			return true;
		} else {
			return false;
		}
	}
}
