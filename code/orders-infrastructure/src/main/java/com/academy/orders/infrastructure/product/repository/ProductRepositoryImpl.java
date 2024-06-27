//package com.academy.orders.infrastructure.product.repository;
//
//import com.academy.orders.domain.product.entity.Product;
//import com.academy.orders.domain.product.repository.ProductRepository;
//import com.academy.orders.infrastructure.product.ProductMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Repository;
//import java.util.List;
//
//@Repository
//@RequiredArgsConstructor
//@Slf4j
//public class ProductRepositoryImpl implements ProductRepository {
//	private final ProductJpaAdapter productJpaAdapter;
//	private final ProductMapper productMapper;
//
//	@Override
//	public List<Product> getAllProducts(String language) {
//		log.debug("Fetching all products by language code");
//		return productMapper.fromEntities(productJpaAdapter.findAllByLanguageCode(language));
//	}
//}
package com.academy.orders.infrastructure.product.repository;

import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.repository.ProductRepository;
import com.academy.orders.domain.product.valueobject.PageRequest;
import com.academy.orders.domain.product.valueobject.PageResponse;
import com.academy.orders.domain.product.valueobject.SortOrder;
import com.academy.orders.infrastructure.product.ProductMapper;
import com.academy.orders.infrastructure.product.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductRepositoryImpl implements ProductRepository {
	private final ProductJpaAdapter productJpaAdapter;
	private final ProductMapper productMapper;

	@Override
	public PageResponse<Product> getAllProducts(String language, PageRequest pageRequest) {
		log.debug("Fetching all products by language code with pagination and sorting");

		Pageable pageable = createPageable(pageRequest);
		Page<ProductEntity> productEntities = productJpaAdapter.findAllByLanguageCode(language, pageable);

		List<Product> products = productMapper.fromEntities(productEntities.getContent());

		return new PageResponse<>(products, productEntities.getTotalPages(), productEntities.getTotalElements(),
				productEntities.getNumber());
	}

	private Pageable createPageable(PageRequest pageRequest) {
		List<Sort.Order> orders = pageRequest.sortOrders().stream()
				.map(sortOrder -> new Sort.Order(
						sortOrder.direction() == SortOrder.Direction.ASC ? Sort.Direction.ASC : Sort.Direction.DESC,
						sortOrder.property()))
				.toList();

		return org.springframework.data.domain.PageRequest.of(pageRequest.page(), pageRequest.size(), Sort.by(orders));
	}
}
