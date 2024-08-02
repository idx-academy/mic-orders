package com.academy.orders.infrastructure.cart.repository;

import com.academy.orders.infrastructure.cart.entity.CartItemEntity;
import com.academy.orders.infrastructure.cart.entity.CartItemId;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemJpaAdapter extends CrudRepository<CartItemEntity, CartItemId> {
	@Query("SELECT c FROM CartItemEntity c INNER JOIN FETCH c.product WHERE c.account.id = :accountId")
	List<CartItemEntity> findAllByAccountId(Long accountId);

	@Modifying
	@Query("DELETE FROM CartItemEntity c WHERE c.account.id =:accountId")
	void deleteAllByAccountId(Long accountId);

	void deleteByAccountIdAndProductId(Long accountId, UUID productId);

	@Query("SELECT c FROM CartItemEntity c JOIN FETCH c.product p JOIN FETCH p.productTranslations t "
			+ " WHERE c.account.id = :accountId AND t.language.code= :lang")
	List<CartItemEntity> findAllByAccountIdAndProductLang(Long accountId, String lang);
}
