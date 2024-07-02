package com.academy.orders.domain.cart.entity;

import com.academy.orders.domain.product.entity.Product;
import lombok.Builder;

@Builder
public record CartItem(Product product, Integer quantity) {
}
