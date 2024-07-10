package com.academy.orders.apirest.orders.mapper;

import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders_api_rest.generated.model.OrderStatusDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static org.junit.Assert.assertThrows;

class OrderStatusMapperTest {
	private final OrderStatusMapper orderStatusMapper = Mappers.getMapper(OrderStatusMapper.class);

	@Test
	void testFromDTO() {
		Assertions.assertEquals(OrderStatus.IN_PROGRESS, orderStatusMapper.fromDTO(OrderStatusDTO.IN_PROGRESS));
		Assertions.assertEquals(OrderStatus.SHIPPED, orderStatusMapper.fromDTO(OrderStatusDTO.SHIPPED));
		Assertions.assertEquals(OrderStatus.DELIVERED, orderStatusMapper.fromDTO(OrderStatusDTO.DELIVERED));
		Assertions.assertEquals(OrderStatus.COMPLETED, orderStatusMapper.fromDTO(OrderStatusDTO.COMPLETED));
		Assertions.assertEquals(OrderStatus.CANCELED, orderStatusMapper.fromDTO(OrderStatusDTO.CANCELED));
	}

	@Test
	void testFromDTOWithInvalidValue() {
		assertThrows(IllegalArgumentException.class, () -> {
			orderStatusMapper.fromDTO(OrderStatusDTO.fromValue("INVALID_STATUS"));
		});
	}
}
