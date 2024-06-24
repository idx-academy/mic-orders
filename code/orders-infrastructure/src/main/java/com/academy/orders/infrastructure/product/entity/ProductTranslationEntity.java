package com.academy.orders.infrastructure.product.entity;

import com.academy.orders.infrastructure.language.entity.LanguageEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
