package com.academy.orders.domain.discount.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Builder
public class Discount {
	private UUID id;
	private int amount;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
}
