package com.academy.orders.infrastructure.cart.repository;

import com.academy.orders.infrastructure.cart.entity.CartItemEntity;
import com.academy.orders.infrastructure.cart.entity.CartItemId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemJpaAdapter extends CrudRepository<CartItemEntity, CartItemId> {
}
