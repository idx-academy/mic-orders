package com.academy.orders.infrastructure.discount.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "discounts")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DiscountEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private int amount;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
}
