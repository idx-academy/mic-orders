package com.academy.orders.infrastructure.order.repository;

import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders.infrastructure.order.entity.OrderEntity;
import java.util.Optional;
import java.util.UUID;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaAdapter extends CrudRepository<OrderEntity, UUID> {
	@Query("select o from OrderEntity o " + "left join fetch o.postAddress pa " + "left join fetch o.orderItems oa "
			+ "left join fetch oa.product p " + "where o.account.id = :accountId")
	PageImpl<OrderEntity> findAllByAccount_Id(Long accountId, Pageable pageable);

	/**
	 * Updates the status of an order identified by its ID.
	 *
	 * @param orderId
	 *            the unique identifier of the order to be updated.
	 * @param orderStatus
	 *            the new status to be set for the order.
	 * @author Anton Bondar
	 */
	@Modifying
	@Transactional
	@Query("update OrderEntity set orderStatus = :orderStatus where id = :orderId")
	void updateOrderStatus(UUID orderId, OrderStatus orderStatus);

	/**
	 * Retrieves an {@link OrderEntity} by its unique identifier.
	 *
	 * @param orderId
	 *            the unique identifier of the order to retrieve, must not be null
	 * @return an {@link Optional} containing the found {@link OrderEntity}, or
	 *         {@link Optional} empty if no order is found with the given identifier
	 * @throws IllegalArgumentException
	 *             if orderId is null
	 */
	@NonNull
	Optional<OrderEntity> findById(@NonNull UUID orderId);
}
