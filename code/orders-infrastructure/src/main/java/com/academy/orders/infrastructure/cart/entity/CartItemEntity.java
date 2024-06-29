package com.academy.orders.infrastructure.cart.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Table(name = "cart_items")
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CartItemEntity {
	@EmbeddedId
	@Builder.Default
	private CartItemId cartItemId = new CartItemId();

	@Column(name = "quantity")
	private int quantity;
}
