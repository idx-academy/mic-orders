package com.academy.orders.infrastructure.product.repository;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.product.dto.ProductManagementFilterDto;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.ProductManagement;
import com.academy.orders.domain.product.entity.ProductTranslationManagement;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders.domain.product.repository.ProductRepository;
import com.academy.orders.infrastructure.common.PageableMapper;
import com.academy.orders.infrastructure.common.repository.ImagesRepositoryImpl;
import com.academy.orders.infrastructure.product.ProductManagementMapper;
import com.academy.orders.infrastructure.product.ProductMapper;
import com.academy.orders.infrastructure.product.ProductPageMapper;
import com.academy.orders.infrastructure.product.ProductTranslationManagementMapper;
import com.academy.orders.infrastructure.product.entity.ProductEntity;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductRepositoryImpl implements ProductRepository {
	private final ProductJpaAdapter productJpaAdapter;
	private final ProductMapper productMapper;
	private final ProductManagementMapper productManagementMapper;
	private final ProductTranslationManagementMapper productTranslationManagementMapper;
	private final ProductPageMapper productPageMapper;
	private final PageableMapper pageableMapper;
	private final ImagesRepositoryImpl imagesRepository;

	@Override
	public Page<Product> getAllProducts(String language, Pageable pageable) {
		log.debug("Fetching all products by language code with pagination and sorting");
		String sort = String.join(",", pageable.sort());
		var productEntities = productJpaAdapter.findAllByLanguageCodeAndStatusVisible(language,
				PageRequest.of(pageable.page(), pageable.size()), sort);

		addLinks(productEntities.getContent());

		List<Product> products = productMapper.fromEntities(productEntities.getContent());

		return new Page<>(productEntities.getTotalElements(), productEntities.getTotalPages(),
				productEntities.isFirst(), productEntities.isLast(), productEntities.getNumber(),
				productEntities.getNumberOfElements(), productEntities.getSize(), productEntities.isEmpty(), products);
	}

	private void addLinks(List<ProductEntity> productEntities) {

		productEntities.forEach(productEntity -> {
			var name = productEntity.getImage().substring(productEntity.getImage().lastIndexOf("/") + 1);
			productEntity.setImage(imagesRepository.getImageLinkByName(name));
		});
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
	public Page<Product> findAllByLanguageWithFilter(String language, @NonNull ProductManagementFilterDto filter,
			Pageable pageableDomain) {
		var pageable = pageableMapper.fromDomain(pageableDomain);
		var ids = productJpaAdapter.findProductsIdsByLangAndFilters(language, filter, pageable);
		var productEntityPage = productJpaAdapter.findProductsByIds(language, ids.getContent(), pageable.getSort());
		return productPageMapper.toDomain(new PageImpl<>(productEntityPage, pageable, ids.getTotalElements()));
	}
}
