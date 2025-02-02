package com.academy.orders.infrastructure.discount.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "discounts")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class DiscountEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private int amount;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
}
