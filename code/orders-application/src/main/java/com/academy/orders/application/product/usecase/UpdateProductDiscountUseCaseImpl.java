package com.academy.orders.application.product.usecase;

import com.academy.orders.domain.discount.entity.Discount;
import com.academy.orders.domain.discount.repository.DiscountRepository;
import com.academy.orders.domain.product.dto.UpdateProductDiscountRequestDto;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.exception.ProductNotFoundException;
import com.academy.orders.domain.product.usecase.UpdateProductDiscountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.academy.orders.domain.product.repository.ProductRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateProductDiscountUseCaseImpl implements UpdateProductDiscountUseCase {
	private final ProductRepository productRepository;
	private final DiscountRepository discountRepository;

	@Override
	public void updateDiscount(UpdateProductDiscountRequestDto updateProductDiscountRequestDto) {
		final Product product = productRepository.getById(updateProductDiscountRequestDto.productId())
				.orElseThrow(() -> new ProductNotFoundException(updateProductDiscountRequestDto.productId()));
		if (Objects.nonNull(product.getDiscount())) {
			discountRepository.deleteById(product.getDiscount().getId());
		}
		final Discount discount = Discount.builder().amount(updateProductDiscountRequestDto.amount())
				.startDate(updateProductDiscountRequestDto.startDate())
				.endDate(updateProductDiscountRequestDto.endDate()).build();
		product.addDiscount(discount);
		productRepository.updateProductDiscount(product.getId(), discount);
	}
}
