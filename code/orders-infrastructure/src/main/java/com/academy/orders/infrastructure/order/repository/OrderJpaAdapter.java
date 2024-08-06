package com.academy.orders.infrastructure.order.repository;

import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders.infrastructure.order.entity.OrderEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
	 * including related entities such as post addresses, order items, and product
	 * ids.
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
	@Query("select o from OrderEntity o left join o.postAddress pa " + "left join o.account a where a.id = :accountId")
	PageImpl<OrderEntity> findAllByAccountId(Long accountId, Pageable pageable);

	/**
	 * Retrieves a list of orders by their IDs, fetching associated product data.
	 * The method uses a complex query to eagerly fetch related entities such as
	 * account, post address, order items, products, product translations,
	 * languages, and tags.
	 *
	 * @param orderIds
	 *            the list of order IDs to retrieve
	 * @param language
	 *            the language code to filter product translations
	 *
	 * @return a list of {@link OrderEntity} with fetched related data
	 */
	@Query("SELECT o FROM OrderEntity o left join fetch o.account a left join fetch o.postAddress pa "
			+ "LEFT JOIN FETCH o.orderItems oi LEFT JOIN FETCH oi.product p "
			+ "LEFT JOIN FETCH p.productTranslations pt LEFT JOIN FETCH pt.language l LEFT JOIN FETCH p.tags t "
			+ "WHERE oi.order.id in (:orderIds) and l.code = :language")
	List<OrderEntity> findAllOrdersByOrderIdsFetchProductData(List<UUID> orderIds, String language);

	/**
	 * Retrieves an OrderEntity by his id, including related entities such as post
	 * addresses, order items, and product ids.
	 *
	 * @param id
	 *            the ID of the account for which orders are to be retrieved.
	 * @return {@link Optional} containing the {@link OrderEntity} if it is present.
	 *
	 * @author Denys Liubchenko
	 */
	@Query("select o from OrderEntity o left join fetch o.postAddress pa left join fetch o.orderItems oa "
			+ "left join fetch oa.product p left join fetch o.account a where o.id = :id")
	Optional<OrderEntity> findByIdFetchOrderItemsData(UUID id);

	/**
	 * Retrieves an OrderEntity by his id, including related entities such as post
	 * addresses and product ids.
	 *
	 * @param id
	 *            the ID of the account for which orders are to be retrieved.
	 * @return {@link Optional} containing the {@link OrderEntity} if it is present.
	 *
	 * @author Denys Liubchenko
	 */
	@Query("select o from OrderEntity o left join fetch o.postAddress pa left join fetch o.account a where o.id = :id")
	Optional<OrderEntity> findByIdFetchData(UUID id);

	/**
	 * Updates the status of an order identified by its ID.
	 *
	 * @param orderId
	 *            the unique identifier of the order to be updated.
	 * @param orderStatus
	 *            the new status to be set for the order.
	 *
	 * @author Anton Bondar
	 */
	@Modifying
	@Query("update OrderEntity set orderStatus = :orderStatus where id = :orderId")
	void updateOrderStatus(UUID orderId, OrderStatus orderStatus);

	/**
	 * Updates the `isPaid` status of an order identified by its ID.
	 *
	 * @param orderId
	 *            the unique identifier of the order to be updated.
	 * @param isPaid
	 *            a boolean indicating whether the order is paid or not. `true` if
	 *            the order is paid, `false` otherwise.
	 *
	 * @author Anton Bondar
	 */
	@Modifying
	@Query("update OrderEntity set isPaid = :isPaid where id = :orderId")
	void updateIsPaidStatus(UUID orderId, Boolean isPaid);
}
