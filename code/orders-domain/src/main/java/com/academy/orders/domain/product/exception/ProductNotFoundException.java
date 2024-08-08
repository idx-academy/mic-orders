package com.academy.orders.domain.product.exception;

import com.academy.orders.domain.common.exception.NotFoundException;
import java.util.UUID;
import lombok.Getter;

@Getter
public class ProductNotFoundException extends NotFoundException {
	private final UUID product;

	public ProductNotFoundException(UUID product) {
		super(String.format("Product %s is not found", product));
		this.product = product;
	}
}
