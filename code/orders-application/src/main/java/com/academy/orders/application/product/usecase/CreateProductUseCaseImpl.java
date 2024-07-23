package com.academy.orders.application.product.usecase;

import com.academy.orders.domain.product.dto.CreateProductRequestDto;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.repository.ProductRepository;
import com.academy.orders.domain.product.usecase.CreateProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateProductUseCaseImpl implements CreateProductUseCase {
	private final ProductRepository productRepository;

	@Override
	public Product createProduct(CreateProductRequestDto product) {

		return productRepository.save(product);
	}
}
