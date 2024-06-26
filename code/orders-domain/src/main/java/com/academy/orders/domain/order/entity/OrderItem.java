package com.academy.orders.domain.order.entity;

import com.academy.orders.domain.product.entity.Product;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record OrderItem(
    Product product,
    Order order,
    BigDecimal price,
    Integer quantity
) {
}
