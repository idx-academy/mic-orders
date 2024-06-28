package com.academy.orders.domain.cart_item.entity;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.product.entity.Product;
import lombok.Builder;

@Builder
public record CartItem(Product product, Account account, Integer quantity) {
}
