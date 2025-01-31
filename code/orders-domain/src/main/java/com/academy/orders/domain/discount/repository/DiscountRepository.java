package com.academy.orders.domain.discount.repository;

import com.academy.orders.domain.discount.entity.Discount;

import java.util.UUID;

public interface DiscountRepository {
	Discount save(Discount discount);
	boolean deleteById(UUID discountId);
}
