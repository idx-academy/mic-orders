package com.academy.orders.domain.order.entity;

import com.academy.orders.domain.product.entity.Product;
import java.math.BigDecimal;

public record OrderItem(Product product, BigDecimal price, Integer quantity) {
}
