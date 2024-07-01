package com.academy.orders.domain.cart.repository;

import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.cart.entity.CreateCartItemDTO;

import java.util.List;
import java.util.UUID;

public interface CartItemRepository {

	/**
	 * Method checks if the cart item exists.
	 *
	 * @param productId
	 *            with type {@link UUID}
	 * @param accountId
	 *            with type {@link Long}
	 *
	 * @return {@link Boolean}
	 *
	 * @author Denys Ryhal, Oleksii Siianchuk
	 */
	Boolean existsByProductIdAndUserId(UUID productId, Long accountId);

	/**
	 * Method saves cartItems to the DB.
	 *
	 * @param cartItem
	 *            with type {@link CreateCartItemDTO}
	 *
	 *
	 * @return {@link CartItem}
	 *
	 * @author Denys Ryhal, Oleksii Siianchuk
	 */
	CartItem save(CreateCartItemDTO cartItem);

	/**
	 * Method increases quantity of the product in the cart by 1.
	 *
	 * @param productId
	 *            with type {@link UUID}
	 * @param accountId
	 *            with type {@link Long}
	 *
	 * @author Denys Ryhal
	 */
	void incrementQuantity(UUID productId, Long accountId);

	List<CartItem> findByAccountEmail(String accountEmail);
}
