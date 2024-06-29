package com.academy.orders.infrastructure.cart.repository;

import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.cart.entity.CreateCartItemDTO;
import com.academy.orders.domain.cart.repository.CartItemRepository;
import com.academy.orders.infrastructure.cart.CartItemMapper;
import com.academy.orders.infrastructure.cart.entity.CartItemEntity;
import com.academy.orders.infrastructure.cart.entity.CartItemId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CartItemRepositoryImpl implements CartItemRepository {
	private final CartItemJpaAdapter cartItemJpaAdapter;
	private final CartItemMapper cartItemMapper;

	@Override
	public Boolean existsByProductIdAndUserId(UUID productId, Long userId) {
		CartItemId cartItemId = new CartItemId();
		cartItemId.setProductId(productId);
		cartItemId.setUserId(userId);
		return cartItemJpaAdapter.existsById(cartItemId);
	}

	@Override
	@Transactional
	public CartItem save(CreateCartItemDTO cartItem) {
		log.info("Saving cart item {}:", cartItem);
		CartItemEntity cartItemEntity = cartItemMapper.toEntity(cartItem);

		CartItemId cartItemId = new CartItemId();
		cartItemId.setProductId(cartItem.productId());
		cartItemId.setUserId(cartItem.userId());

		cartItemEntity.setCartItemId(cartItemId);
		cartItemEntity.setQuantity(cartItem.quantity());

		CartItemEntity savedCartItemEntity = cartItemJpaAdapter.save(cartItemEntity);
		return cartItemMapper.fromEntity(savedCartItemEntity);
	}

}
