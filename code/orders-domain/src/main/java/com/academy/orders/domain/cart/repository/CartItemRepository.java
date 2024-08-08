package com.academy.orders.domain.cart.repository;

import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.cart.entity.CreateCartItemDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing shopping cart.
 */
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
	 * Method finds {@link CartItem} by accountId.
	 *
	 * @param accountId
	 *            id of logged-in user.
	 * @return {@link List} of {@link CartItem}
	 * @author Denys Ryhal
	 */
	List<CartItem> findCartItemsByAccountId(Long accountId);

	/**
	 * Method delete all {@link CartItem cartItems} by accountId.
	 *
	 * @param accountId
	 *            id of the logged-in user.
	 * @author Denys Ryhal
	 */
	void deleteCartItemsByAccountId(Long accountId);

	/**
	 * Method delete {@link CartItem cartItem} by accountId and productId
	 *
	 * @param accountId
	 *            id of the logged-in user.
	 * @param productId
	 *            id of the product.
	 * @author Denys Ryhal
	 */
	void deleteCartItemByAccountAndProductIds(Long accountId, UUID productId);

	/**
	 * Method finds List of {@link CartItem cartItem} by accountId and language
	 *
	 * @param accountId
	 *            id of the logged-in user.
	 * @param lang
	 *            translation of the product.
	 * @author Denys Ryhal
	 */
	List<CartItem> findCartItemsByAccountIdAndLang(Long accountId, String lang);

	/**
	 * Method to get the quantity of a product in the cart for a specific user.
	 *
	 * @param productId
	 *            UUID of the product.
	 * @param userId
	 *            Long ID of the user.
	 * @return Optional<CartItem>
	 */
	Optional<CartItem> findByProductIdAndUserId(UUID productId, Long userId);
}
