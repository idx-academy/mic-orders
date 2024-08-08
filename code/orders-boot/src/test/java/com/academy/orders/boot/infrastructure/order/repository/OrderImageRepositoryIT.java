package com.academy.orders.boot.infrastructure.order.repository;

import com.academy.orders.boot.Application;
import com.academy.orders.domain.order.repository.OrderImageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.academy.orders.ModelUtils.getOrderWithoutId;
import static com.academy.orders.ModelUtils.getProductWithImageLink;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class)
@ActiveProfiles("it")
class OrderImageRepositoryIT {
	@Autowired
	private OrderImageRepository orderImageRepository;

	@Test
	void loadOrderItemForProductInOrderItemTest() {
		var order = getOrderWithoutId();
		var productWithImageLink = getProductWithImageLink();

		var actual = orderImageRepository.loadImageForProductInOrder(order);

		assertEquals(order.postAddress(), actual.postAddress());
		assertEquals(order.receiver(), actual.receiver());
		assertEquals(order.receiver(), actual.receiver());
		assertEquals(productWithImageLink, actual.orderItems().get(0).product());
	}
}
