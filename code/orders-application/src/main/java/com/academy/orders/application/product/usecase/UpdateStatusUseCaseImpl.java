package com.academy.orders.application.product.usecase;

import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders.domain.product.exception.ProductNotFoundException;
import com.academy.orders.domain.product.repository.ProductRepository;
import com.academy.orders.domain.product.usecase.UpdateStatusUseCase;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateStatusUseCaseImpl implements UpdateStatusUseCase {
	private final ProductRepository productRepository;

	@Override
	@Transactional
	public void updateStatus(UUID productId, ProductStatus status) {
		if (!productRepository.existById(productId)) {
			throw new ProductNotFoundException(productId);
		}
		productRepository.updateStatus(productId, status);
	}
}
