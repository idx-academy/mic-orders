package com.academy.orders.infrastructure.cart;

import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.cart.entity.CreateCartItemDTO;
import com.academy.orders.infrastructure.cart.entity.CartItemEntity;
import com.academy.orders.infrastructure.product.ProductMapper;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ProductMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartItemMapper {

	@Mapping(target = "product.tags", ignore = true)
	@Mapping(target = "product.productTranslations", ignore = true)
	CartItem fromEntity(CartItemEntity cartItem);
	CartItemEntity toEntity(CreateCartItemDTO cartItem);

	List<CartItem> fromEntities(List<CartItemEntity> cartItemEntities);

}
