package com.academy.orders.application.product.usecase;

import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.repository.ProductRepository;
import com.academy.orders.domain.product.usecase.GetAllProductsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllProductsUseCaseImpl implements GetAllProductsUseCase {

	private final ProductRepository productRepository;

	@Override
	public List<Product> getAllProducts() {
		return productRepository.getAllProducts();
	}
}
