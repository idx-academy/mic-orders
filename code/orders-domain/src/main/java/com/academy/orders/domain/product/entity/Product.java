package com.academy.orders.domain.product.entity;

import com.academy.orders.domain.discount.entity.Discount;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Product {
	private UUID id;
	private ProductStatus status;
	@With
	private String image;
	private LocalDateTime createdAt;
	private Integer quantity;
	private BigDecimal price;
	private BigDecimal priceWithDiscount;
	private Discount discount;
	private Set<Tag> tags;
	private Set<ProductTranslation> productTranslations;

	public Product applyDiscount() {
		if (discount != null) {
			BigDecimal percentage = BigDecimal.valueOf(100 - discount.getAmount()).divide(BigDecimal.valueOf(100));
			this.priceWithDiscount = price.multiply(percentage).setScale(2);
		}
		return this;
	}

	public void addDiscount(final Discount discount) {
		this.discount = discount;
		applyDiscount();
	}
}
