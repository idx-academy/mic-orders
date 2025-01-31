package com.academy.orders.infrastructure.discount;

import com.academy.orders.domain.discount.entity.Discount;
import com.academy.orders.infrastructure.discount.entity.DiscountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiscountMapper {
	Discount fromEntity(DiscountEntity discountEntity);
	DiscountEntity toEntity(Discount discount);
}
