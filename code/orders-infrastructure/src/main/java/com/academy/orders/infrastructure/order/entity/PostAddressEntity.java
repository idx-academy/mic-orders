package com.academy.orders.infrastructure.order.entity;

import com.academy.orders.domain.order.entity.enumerated.DeliveryMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "post_addresses")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "order")
@ToString(exclude = "order")
@Entity
public class PostAddressEntity {
	@Id
	private UUID id;

	@Column(name = "delivery_method", nullable = false)
	private DeliveryMethod deliveryMethod;

	@Column(nullable = false)
	private String city;

	@Column(nullable = false)
	private String department;

	@MapsId
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id", nullable = false)
	private OrderEntity order;
}
