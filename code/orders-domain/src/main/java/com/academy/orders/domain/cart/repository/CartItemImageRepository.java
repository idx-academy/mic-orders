package com.academy.orders.domain.cart.repository;

import com.academy.orders.domain.cart.entity.CartItem;

public interface CartItemImageRepository {
    CartItem loadImageForProductInCart(CartItem cartItem);
}
