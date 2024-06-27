package com.academy.orders.infrastructure.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Embeddable
@NoArgsConstructor
@ToString
public class OrderItemId implements Serializable {
	@Column(name = "order_id", nullable = false)
	private String orderId;

	@Column(name = "product_id", nullable = false)
	private String productId;
}
