package com.academy.orders.domain.order.repository;

import com.academy.orders.domain.order.entity.Order;

/**
 * Repository interface for managing and loading images for products in an
 * order.
 */
public interface OrderImageRepository {
	/**
	 * Loads the images for the products associated with the given order.
	 *
	 * @param order
	 *            the {@link Order} for which the products images needs to be
	 *            loaded.
	 * @return the updated {@link Order} with the products images loaded.
	 *
	 * @author Denys Ryhal
	 */
	Order loadImageForProductInOrder(Order order);
}
