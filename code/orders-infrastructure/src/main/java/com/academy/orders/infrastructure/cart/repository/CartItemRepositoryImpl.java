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
import java.util.Optional;
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

		CartItemId cartItemId = new CartItemId(cartItem.productId(), cartItem.userId());
		Optional<CartItemEntity> existingCartItemEntity = cartItemJpaAdapter.findById(cartItemId);

		CartItemEntity cartItemEntity;

		if (existingCartItemEntity.isPresent()) {
			cartItemEntity = existingCartItemEntity.get();
			cartItemEntity.setQuantity(cartItem.quantity());
		} else {
			cartItemEntity = cartItemMapper.toEntity(cartItem);
			setProduct(cartItemEntity, cartItem.productId());
			setAccount(cartItemEntity, cartItem.userId());
		}
		return cartItemMapper.fromEntity(cartItemJpaAdapter.save(cartItemEntity));
	}

	private void setProduct(CartItemEntity cartItemEntity, UUID productId) {
		cartItemEntity.setProduct(productJpaAdapter.getReferenceById(productId));
	}

	private void setAccount(CartItemEntity cartItemEntity, Long accountId) {
		cartItemEntity.setAccount(accountJpaAdapter.getReferenceById(accountId));
	}

	@Override
	public List<CartItem> findCartItemsByAccountId(Long accountId) {
		var cartItems = cartItemJpaAdapter.findAllByAccountId(accountId);
		return cartItemMapper.fromEntities(cartItems);
	}

	@Override
	@Transactional
	public void deleteCartItemsByAccountId(Long accountId) {
		cartItemJpaAdapter.deleteAllByAccountId(accountId);
	}

	@Override
	@Transactional
	public void deleteCartItemByAccountAndProductIds(Long accountId, UUID productId) {
		cartItemJpaAdapter.deleteByAccountIdAndProductId(accountId, productId);
	}

	@Override
	public List<CartItem> findCartItemsByAccountIdAndLang(Long accountId, String lang) {
		var cartItems = cartItemJpaAdapter.findAllByAccountIdAndProductLang(accountId, lang);
		return cartItemMapper.fromEntitiesWithProductsTranslations(cartItems);
	}

	@Override
	public Optional<CartItem> findByProductIdAndUserId(UUID productId, Long userId) {
		var cartItem = cartItemJpaAdapter.findById(new CartItemId(productId, userId));
		return cartItem.map(cartItemMapper::fromEntity);
	}

}
