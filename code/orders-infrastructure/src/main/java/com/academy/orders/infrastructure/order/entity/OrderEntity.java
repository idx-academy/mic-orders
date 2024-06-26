package com.academy.orders.infrastructure.order.entity;

import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders.infrastructure.account.entity.AccountEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import static java.time.LocalDateTime.now;

@Table(name = "orders")
@Entity
@Data
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"account", "postAddress", "orderItems"})
@ToString(exclude = {"account", "postAddress", "orderItems"})
public class OrderEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "is_paid", nullable = false)
	private Boolean isPaid;

	@Column(name = "order_status", nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	@Column(name = "created_at", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;

	@Column(name = "edited_at", nullable = false)
	@UpdateTimestamp
	private LocalDateTime editedAt;

	@Embedded
	private OrderReceiverVO receiver;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "account_id", nullable = false)
	private AccountEntity account;

	@OneToOne(mappedBy = "order", fetch = FetchType.LAZY)
	private PostAddressEntity postAddress;

	@Setter(AccessLevel.PRIVATE)
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	@Builder.Default
	private List<OrderItemEntity> orderItems = new ArrayList<>();
}
