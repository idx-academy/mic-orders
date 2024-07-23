package com.academy.orders.infrastructure.product.entity;

import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders.infrastructure.tag.entity.TagEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@EqualsAndHashCode(exclude = {"tags", "productTranslations"})
// @ToString(exclude = {"tags", "productTranslations"})
@ToString
public class ProductEntity {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private ProductStatus status;

	@Column(name = "image_link", nullable = false)
	private String image;

	// @Setter(AccessLevel.PRIVATE)
	@Setter
	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@Column(name = "price", nullable = false)
	private BigDecimal price;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "products_tags", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private Set<TagEntity> tags;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ProductTranslationEntity> productTranslations;
}
