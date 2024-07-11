package com.academy.orders.apirest.orders.mapper;

import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders_api_rest.generated.model.OrderStatusDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class OrderStatusMapperTest {
	private final OrderStatusMapper orderStatusMapper = Mappers.getMapper(OrderStatusMapper.class);

	@Test
	void fromDTOTest() {
		assertEquals(OrderStatus.IN_PROGRESS, orderStatusMapper.fromDTO(OrderStatusDTO.IN_PROGRESS));
		assertEquals(OrderStatus.SHIPPED, orderStatusMapper.fromDTO(OrderStatusDTO.SHIPPED));
		assertEquals(OrderStatus.DELIVERED, orderStatusMapper.fromDTO(OrderStatusDTO.DELIVERED));
		assertEquals(OrderStatus.COMPLETED, orderStatusMapper.fromDTO(OrderStatusDTO.COMPLETED));
		assertEquals(OrderStatus.CANCELED, orderStatusMapper.fromDTO(OrderStatusDTO.CANCELED));
	}

	@Test
	void fromDTOWithInvalidValueTest() {
		assertThrowsExactly(IllegalArgumentException.class,
				() -> orderStatusMapper.fromDTO(OrderStatusDTO.fromValue("INVALID_STATUS")));
	}
}
