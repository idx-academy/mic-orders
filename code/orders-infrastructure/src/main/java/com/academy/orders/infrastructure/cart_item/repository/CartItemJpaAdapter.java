package com.academy.orders.infrastructure.cart_item.repository;

import com.academy.orders.infrastructure.cart_item.entity.CartItemEntity;
import com.academy.orders.infrastructure.cart_item.entity.CartItemId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemJpaAdapter extends CrudRepository<CartItemEntity, CartItemId> {
}
