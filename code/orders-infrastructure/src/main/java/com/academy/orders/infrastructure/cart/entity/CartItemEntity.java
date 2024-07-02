package com.academy.orders.infrastructure.cart.entity;

import com.academy.orders.infrastructure.account.entity.AccountEntity;
import com.academy.orders.infrastructure.product.entity.ProductEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@MapsId("userId")
	@JoinColumn(name = "user_id", nullable = false)
	private AccountEntity account;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@MapsId("productId")
	@JoinColumn(name = "product_id", nullable = false)
	private ProductEntity product;

}
