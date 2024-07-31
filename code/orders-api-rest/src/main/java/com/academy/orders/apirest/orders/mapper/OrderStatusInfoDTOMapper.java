package com.academy.orders.apirest.orders.mapper;

import com.academy.orders.domain.order.dto.OrderStatusInfo;
import com.academy.orders_api_rest.generated.model.OrderStatusInfoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderStatusInfoDTOMapper {
	OrderStatusInfoDTO toDTO(OrderStatusInfo orderStatusInfo);
}
