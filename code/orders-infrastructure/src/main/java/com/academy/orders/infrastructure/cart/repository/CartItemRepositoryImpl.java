package com.academy.orders.infrastructure.cart.repository;

import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.cart.entity.CreateCartItemDTO;
import com.academy.orders.domain.cart.repository.CartItemRepository;
import com.academy.orders.infrastructure.account.repository.AccountJpaAdapter;
import com.academy.orders.infrastructure.cart.CartItemMapper;
import com.academy.orders.infrastructure.cart.entity.CartItemEntity;
import com.academy.orders.infrastructure.cart.entity.CartItemId;
import com.academy.orders.infrastructure.product.repository.ProductJpaAdapter;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CartItemRepositoryImpl implements CartItemRepository {
	private final CartItemJpaAdapter cartItemJpaAdapter;
	private final CartItemMapper cartItemMapper;
	private final AccountJpaAdapter accountJpaAdapter;
	private final ProductJpaAdapter productJpaAdapter;

	@Override
	public Boolean existsByProductIdAndUserId(UUID productId, Long accountId) {
		return cartItemJpaAdapter.existsById(new CartItemId(productId, accountId));
	}

	@Override
	@Transactional
	public CartItem save(CreateCartItemDTO cartItem) {
		log.info("Saving cart item {}:", cartItem);
		var cartItemEntity = cartItemMapper.toEntity(cartItem);
		setProduct(cartItemEntity, cartItem.productId());
		setAccount(cartItemEntity, cartItem.userId());

		return cartItemMapper.fromEntity(cartItemJpaAdapter.save(cartItemEntity));
	}

	private void setProduct(CartItemEntity cartItemEntity, UUID productId) {
		cartItemEntity.setProduct(productJpaAdapter.getReferenceById(productId));
	}

	private void setAccount(CartItemEntity cartItemEntity, Long accountId) {
		cartItemEntity.setAccount(accountJpaAdapter.getReferenceById(accountId));
	}

	@Override
	@Transactional
	public void incrementQuantity(UUID productId, Long accountId) {
		cartItemJpaAdapter.increaseQuantity(new CartItemId(productId, accountId), 1);
	}

	@Override
	public List<CartItem> findCartItemsByAccountId(Long accountId) {
		return cartItemMapper.fromEntities(cartItemJpaAdapter.findAllByAccountId(accountId));
	}

	@Override
	@Transactional
	public void deleteUsersCartItems(Long accountId) {
		cartItemJpaAdapter.deleteAllByAccountId(accountId);
	}

}