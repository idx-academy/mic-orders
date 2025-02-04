package com.academy.orders.apirest.orders.mapper;

import com.academy.orders.apirest.common.mapper.LocalDateTimeMapper;
import com.academy.orders.apirest.products.mapper.ProductPreviewDTOMapper;
import com.academy.orders.domain.order.dto.CreateOrderDto;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders_api_rest.generated.model.ManagerOrderDTO;
import com.academy.orders_api_rest.generated.model.ManagerOrderPreviewDTO;
import com.academy.orders_api_rest.generated.model.UserOrderDTO;
import com.academy.orders_api_rest.generated.model.PlaceOrderRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductPreviewDTOMapper.class, LocalDateTimeMapper.class})
public interface OrderDTOMapper {
	UserOrderDTO toUserDto(Order order);
	ManagerOrderDTO toManagerDto(Order order);
	ManagerOrderPreviewDTO toManagerOrderPreviewDto(Order order);
	CreateOrderDto toCreateOrderDto(PlaceOrderRequestDTO placeOrderRequestDTO);
}
