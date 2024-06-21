package com.academy.orders.infrastructure.product.entity;

import com.academy.orders.infrastructure.language.entity.LanguageEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "products_translations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@EqualsAndHashCode(exclude = {"product", "language"})
@ToString(exclude = {"product", "language"})
public class ProductTranslationEntity {
	@EmbeddedId
	private ProductTranslationId productTranslationId;

	@Column(nullable = false)
	private String name;

	@Column(length = 1000)
	private String description;

	@MapsId("productId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private ProductEntity product;

	@MapsId("languageId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "language_id", nullable = false)
	private LanguageEntity language;
}
