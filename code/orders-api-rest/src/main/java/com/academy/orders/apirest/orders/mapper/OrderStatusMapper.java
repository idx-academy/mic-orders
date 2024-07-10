package com.academy.orders.apirest.orders.mapper;

import com.academy.orders.apirest.common.mapper.LocalDateTimeMapper;
import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders_api_rest.generated.model.OrderStatusDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {LocalDateTimeMapper.class})
public interface OrderStatusMapper {
	OrderStatus fromDTO(OrderStatusDTO orderStatusDTO);
}
