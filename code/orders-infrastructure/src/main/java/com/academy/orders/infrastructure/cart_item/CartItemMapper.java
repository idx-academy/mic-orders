package com.academy.orders.infrastructure.cart_item;

import com.academy.orders.domain.cart_item.entity.CartItem;
import com.academy.orders.domain.cart_item.entity.CreateCartItemDTO;
import com.academy.orders.infrastructure.cart_item.entity.CartItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
	CartItem fromEntity(CartItemEntity cartItem);

	CartItemEntity toEntity(CreateCartItemDTO cartItem);
}
