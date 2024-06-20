package com.academy.orders.infrastructure.tag.entity;

import com.academy.orders.infrastructure.product.entity.ProductEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Table(name = "tags")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@EqualsAndHashCode(exclude = {"products"})
@ToString(exclude = {"products"})
public class TagEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tags_generator")
	@SequenceGenerator(name = "tags_generator", sequenceName = "TAGS_id_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@ManyToMany(mappedBy = "tags")
	private Set<ProductEntity> products;
}
