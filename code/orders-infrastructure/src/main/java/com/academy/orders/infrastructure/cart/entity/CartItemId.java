package com.academy.orders.infrastructure.cart.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Data
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CartItemId {
	private UUID productId;
	private Long userId;
}
