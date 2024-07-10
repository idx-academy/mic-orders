package com.academy.orders.infrastructure.order.repository;

import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders.infrastructure.order.entity.OrderEntity;
import java.util.UUID;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaAdapter extends CrudRepository<OrderEntity, UUID> {
	/**
	 * Retrieves a paginated list of OrderEntity objects for a specific account,
	 * including related entities such as post addresses, order items, and products.
	 *
	 * @param accountId
	 *            the ID of the account for which orders are to be retrieved.
	 * @param pageable
	 *            the pagination information.
	 * @return {@link PageImpl} containing the list of {@link OrderEntity} objects
	 *         along with pagination details.
	 *
	 * @author Denys Liubchenko
	 */
	@Query("select o from OrderEntity o left join fetch o.postAddress pa left join fetch o.orderItems oa "
			+ "left join fetch oa.product p where o.account.id = :accountId")
	PageImpl<OrderEntity> findAllByAccountId(Long accountId, Pageable pageable);

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
}
