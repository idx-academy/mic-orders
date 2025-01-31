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
public class Product {
	private UUID id;
	private ProductStatus status;
	@With
	private String image;
	private LocalDateTime createdAt;
	private Integer quantity;
	private BigDecimal price;
	private BigDecimal originalPrice;
	private Discount discount;
	private Set<Tag> tags;
	private Set<ProductTranslation> productTranslations;

	public Product definePrice() {
		BigDecimal temp = price;
		if (discount != null) {
			double priceWithDiscount = price.doubleValue() * (1 - (double) discount.getAmount() / 100);
			price = BigDecimal.valueOf(priceWithDiscount).setScale(2, RoundingMode.HALF_UP);
			originalPrice = temp;
		}
		return this;
	}

	public static BigDecimal calculatePrice(BigDecimal originalPrice, int amount) {
		BigDecimal discountMultiplier = BigDecimal.valueOf(originalPrice.doubleValue() * (1 - amount / 100.));
		return discountMultiplier.setScale(2, BigDecimal.ROUND_HALF_UP);

	}

	public void addDiscount(final Discount discount) {
		validateDiscount(discount);
		this.discount = discount;
	}

	private void validateDiscount(Discount discount) {
		if (discount == null) {
			throw new IllegalArgumentException("Discount cannot be null");
		}
		if (discount.getId() != null) {
			throw new IllegalArgumentException("Cannot reuse existing discount");
		}
		if (discount.getStartDate().isAfter(discount.getEndDate())) {
			throw new IllegalArgumentException("Discount start date must be before end date");
		}
	}
}
