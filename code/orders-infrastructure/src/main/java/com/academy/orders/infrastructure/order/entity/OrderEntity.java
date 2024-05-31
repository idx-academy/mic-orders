package com.academy.orders.infrastructure.order.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Setter;

@Table
@Data
@Setter
@Entity(name = "ORDER")
public class OrderEntity {

	@Id
	@Column(name = "ORDER_ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
}
