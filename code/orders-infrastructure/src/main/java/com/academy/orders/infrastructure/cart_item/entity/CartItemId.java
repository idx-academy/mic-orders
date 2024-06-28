package com.academy.orders.infrastructure.cart_item.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table
@Data
@Setter
@Embeddable
@NoArgsConstructor
public class CartItemId {
	@Column(name = "product_id", nullable = false)
	private UUID productId;

	@Column(name = "user_id", nullable = false)
	private Long userId;
}
