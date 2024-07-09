package com.academy.orders.apirest.orders.mapper;

import com.academy.orders.apirest.common.mapper.LocalDateTimeMapper;
import com.academy.orders.apirest.products.mapper.ProductPreviewDTOMapper;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders_api_rest.generated.model.PageOrderDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OrderDTOMapper.class, ProductPreviewDTOMapper.class,
		LocalDateTimeMapper.class})
public interface PageOrderDTOMapper {
	PageOrderDTO toDto(Page<Order> orderPage);
}
