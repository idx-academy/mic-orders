package com.academy.orders.apirest.orders.mapper;

import com.academy.orders.apirest.common.mapper.LocalDateTimeMapper;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders_api_rest.generated.model.PageManagerOrderPreviewDTO;
import com.academy.orders_api_rest.generated.model.PageUserOrderDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OrderDTOMapper.class, LocalDateTimeMapper.class})
public interface PageOrderDTOMapper {
	PageUserOrderDTO toUserDto(Page<Order> orderPage);
	PageManagerOrderPreviewDTO toManagerDto(Page<Order> orderPage);
}
