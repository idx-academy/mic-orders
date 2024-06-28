package com.academy.orders.application.order.usecase;

import com.academy.orders.domain.order.usecase.ChangeQuantityUseCase;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.repository.ProductRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeQuantityUseCaseImpl implements ChangeQuantityUseCase {
	private final ProductRepository productRepository;

	@Override
	public void changeQuantityOfProduct(Product product, Integer orderedQuantity) {
		var quantityOfProductsLeft = getQuantityOfProductsLeft(product, orderedQuantity);
		setNewQuantity(product.id(), quantityOfProductsLeft);
	}

	private int getQuantityOfProductsLeft(Product product, Integer value) {
		var quantityDifference = product.quantity() - value;
		if (quantityDifference < 0)
			throw new IllegalArgumentException("");

		return quantityDifference;
	}

	private void setNewQuantity(UUID productId, Integer quantity) {
		productRepository.setNewProductQuantity(productId, quantity);
	}
}
