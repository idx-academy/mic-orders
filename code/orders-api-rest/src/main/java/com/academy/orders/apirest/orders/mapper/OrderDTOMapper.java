package com.academy.orders.apirest.orders.mapper;

import com.academy.orders.domain.order.dto.CreateOrderDto;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders_api_rest.generated.model.OrderDTO;
import com.academy.orders_api_rest.generated.model.PlaceOrderRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderDTOMapper {

	OrderDTO toDto(Order order);

	CreateOrderDto toCreateOrderDto(PlaceOrderRequestDTO placeOrderRequestDTO);
}
