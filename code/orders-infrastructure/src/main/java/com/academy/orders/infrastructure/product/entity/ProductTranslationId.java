package com.academy.orders.infrastructure.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Data
@Setter
@Embeddable
@NoArgsConstructor
public class ProductTranslationId implements Serializable {
	@Column(name = "product_id")
	private String productId;

	@Column(name = "language_id")
	private Long languageId;
}
