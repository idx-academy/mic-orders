package com.academy.orders.infrastructure.discount.repository;

import com.academy.orders.domain.discount.entity.Discount;
import com.academy.orders.domain.discount.repository.DiscountRepository;
import com.academy.orders.infrastructure.discount.DiscountMapper;
import com.academy.orders.infrastructure.discount.entity.DiscountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Transactional
public class DiscountRepositoryImpl implements DiscountRepository {
	private final DiscountJpaAdapter discountJpaAdapter;
	private final DiscountMapper discountMapper;

	@Override
	public Discount save(Discount discount) {
		final DiscountEntity discountEntity = discountJpaAdapter.save(discountMapper.toEntity(discount));
		return discountMapper.fromEntity(discountEntity);
	}

	@Override
	public boolean deleteById(UUID discountId) {
		if (discountJpaAdapter.existsById(discountId)) {
			discountJpaAdapter.deleteById(discountId);
			return true;
		}
		return false;

	}
}
