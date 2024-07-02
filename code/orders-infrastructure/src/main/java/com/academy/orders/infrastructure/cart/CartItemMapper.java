package com.academy.orders.infrastructure.cart;

import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.cart.entity.CreateCartItemDTO;
import com.academy.orders.infrastructure.cart.entity.CartItemEntity;
import com.academy.orders.infrastructure.product.ProductMapper;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface CartItemMapper {
	CartItem fromEntity(CartItemEntity cartItem);
	CartItemEntity toEntity(CreateCartItemDTO cartItem);
	List<CartItem> fromEntities(List<CartItemEntity> cartItemEntities);
}
