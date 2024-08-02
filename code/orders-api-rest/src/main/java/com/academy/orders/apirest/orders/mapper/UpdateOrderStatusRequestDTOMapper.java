package com.academy.orders.apirest.orders.mapper;

import com.academy.orders.domain.order.dto.UpdateOrderStatusDto;
import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders_api_rest.generated.model.OrderStatusDTO;
import com.academy.orders_api_rest.generated.model.UpdateOrderStatusRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UpdateOrderStatusRequestDTOMapper {
	@Mapping(source = "orderStatus", target = "status")
	@Mapping(source = "isPaid", target = "isPaid")
	UpdateOrderStatusDto fromDTO(UpdateOrderStatusRequestDTO dto);

	default OrderStatus mapOrderStatusDTOToOrderStatus(OrderStatusDTO orderStatusDTO) {
		if (orderStatusDTO == null) {
			return null;
		}
		return OrderStatus.valueOf(orderStatusDTO.name());
	}
}
