package com.academy.orders.infrastructure.cart.repository;

import com.academy.orders.infrastructure.cart.entity.CartItemEntity;
import com.academy.orders.infrastructure.cart.entity.CartItemId;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemJpaAdapter extends CrudRepository<CartItemEntity, CartItemId> {
	@Modifying
	@Query("UPDATE CartItemEntity ci SET ci.quantity = ci.quantity + :quantity " + "WHERE ci.cartItemId = :cartItemId ")
	void increaseQuantity(CartItemId cartItemId, Integer quantity);

	@Query("SELECT c FROM CartItemEntity c INNER JOIN FETCH c.product WHERE c.account.id = :accountId")
	List<CartItemEntity> findAllByAccountId(Long accountId);

	@Modifying
	@Query("DELETE FROM CartItemEntity c WHERE c.account.id =:accountId")
	void deleteAllByAccountId(Long accountId);
}
