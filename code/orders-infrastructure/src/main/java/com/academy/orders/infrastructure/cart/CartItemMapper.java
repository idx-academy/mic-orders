package com.academy.orders.infrastructure.cart;

import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.cart.entity.CreateCartItemDTO;
import com.academy.orders.infrastructure.cart.entity.CartItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
	CartItem fromEntity(CartItemEntity cartItem);

	CartItemEntity toEntity(CreateCartItemDTO cartItem);
}
