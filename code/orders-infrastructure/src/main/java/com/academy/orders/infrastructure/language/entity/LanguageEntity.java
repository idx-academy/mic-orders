package com.academy.orders.infrastructure.language.entity;

import com.academy.orders.infrastructure.product.entity.ProductTranslationEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.util.Set;

@Table(name = "languages")
@Data
@Setter
@Entity
public class LanguageEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 5)
	private String code;

	@OneToMany(mappedBy = "language", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ProductTranslationEntity> productTranslations;
}
