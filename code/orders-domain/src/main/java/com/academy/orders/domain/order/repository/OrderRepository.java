package com.academy.orders.domain.order.repository;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.order.dto.OrdersFilterParametersDto;
import java.util.Optional;
import java.util.UUID;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.enumerated.OrderStatus;

public interface OrderRepository {
	/**
	 * Method find {@link Order} by order's id
	 *
	 * @param id
	 *            with type {@link UUID}
	 *
	 * @return {@link Optional} of {@link Order}
	 */
	Optional<Order> findById(UUID id);

	/**
	 * Method saves order to the DB.
	 *
	 * @param accountId
	 *            id of logged-in user
	 * @param order
	 *            to save
	 *
	 * @return {@link UUID} id of created order
	 * @author Denys Ryhal
	 */
	UUID save(Order order, Long accountId);

	/**
	 * Method find page of {@link Order} by account id, fetch products by language
	 *
	 * @param userId
	 *            the ID of the user whose orders are to be retrieved.
	 * @param language
	 *            the language in which the orders should be retrieved.
	 * @param pageable
	 *            the pagination and sorting information.
	 * @return a Page of OrderEntity objects matching the specified criteria.
	 *
	 * @author Denys Liubchenko
	 */
	Page<Order> findAllByUserId(Long userId, String language, Pageable pageable);

	/**
	 * Updates the status of an order identified by its ID.
	 *
	 * @param orderId
	 *            the unique identifier of the order to be updated.
	 * @param orderStatus
	 *            the new status to be set for the order.
	 * @author Anton Bondar
	 */
	void updateOrderStatus(UUID orderId, OrderStatus orderStatus);

	/**
	 * Method find page of {@link Order} by account id, fetch products by language
	 *
	 * @param filterParametersDto
	 *            filter parameters to sort orders.
	 * @param language
	 *            the language in which the orders should be retrieved.
	 * @param pageable
	 *            the pagination and sorting information.
	 * @return a Page of OrderEntity objects matching the specified criteria.
	 *
	 * @author Denys Liubchenko
	 */
	Page<Order> findAll(OrdersFilterParametersDto filterParametersDto, String language, Pageable pageable);
}
