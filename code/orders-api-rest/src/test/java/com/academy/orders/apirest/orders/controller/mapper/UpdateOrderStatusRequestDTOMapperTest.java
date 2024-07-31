package com.academy.orders.apirest.orders.controller.mapper;

import com.academy.orders.apirest.orders.mapper.UpdateOrderStatusRequestDTOMapper;
import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders_api_rest.generated.model.OrderStatusDTO;
import com.academy.orders_api_rest.generated.model.UpdateOrderStatusRequestDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class UpdateOrderStatusRequestDTOMapperTest {
	private UpdateOrderStatusRequestDTOMapper mapper;

	@BeforeEach
	void setUp() {
		mapper = Mappers.getMapper(UpdateOrderStatusRequestDTOMapper.class);
	}

	@Test
	void fromDTOWithValidValuesTest() {
		var requestDTO = new UpdateOrderStatusRequestDTO();
		requestDTO.setOrderStatus(OrderStatusDTO.COMPLETED);
		requestDTO.setIsPaid(true);

		var dto = mapper.fromDTO(requestDTO);

		Assertions.assertEquals(OrderStatus.COMPLETED, dto.status());
		Assertions.assertEquals(true, dto.isPaid());
	}

	@Test
	void fromDTOWithNullValuesTest() {
		var requestDTO = new UpdateOrderStatusRequestDTO();

		requestDTO.setOrderStatus(null);
		requestDTO.setIsPaid(null);

		var dto = mapper.fromDTO(requestDTO);

		Assertions.assertNull(dto.status());
		Assertions.assertNull(dto.isPaid());
	}

	@Test
	void mapOrderStatusDTOToOrderStatusWithValidDTOTest() {
		var orderStatusDTO = OrderStatusDTO.SHIPPED;
		var orderStatus = mapper.mapOrderStatusDTOToOrderStatus(orderStatusDTO);
		Assertions.assertEquals(OrderStatus.SHIPPED, orderStatus);
	}

	@Test
	void mapOrderStatusDTOToOrderStatusWithNullDTOTest() {
		var orderStatus = mapper.mapOrderStatusDTOToOrderStatus(null);
		Assertions.assertNull(orderStatus);
	}
}
