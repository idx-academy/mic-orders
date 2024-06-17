package com.academy.orders.infrastructure.order.repository;

import java.util.Optional;
import java.util.UUID;

import com.academy.colors_api.generated.api.ColorsApi;
import com.academy.orders.domain.repository.OrderRepository;
import com.academy.orders.infrastructure.order.OrderMapper;
import com.academy.orders.domain.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderRepositoryImpl implements OrderRepository {

	private final OrderJpaAdapter jpaAdapter;

	private final OrderMapper mapper;

	// TODO remove, added for example. It should be created separate repo
	// ColorsRepository
	private final ColorsApi colorsApi;

	@Override
	public Optional<Order> findById(UUID id) {
		try {
			// TODO delete me
			final var colors = colorsApi.getColors();
			log.info("Retrieved colors {}", colors);
		} catch (Exception e) {
			log.error("Ops", e);
		}

		return jpaAdapter.findById(id).map(mapper::fromEntity);
	}
}
