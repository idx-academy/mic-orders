package com.academy.orders.infrastructure.order;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.infrastructure.common.PageableMapper;
import com.academy.orders.infrastructure.order.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.springframework.data.domain.PageImpl;

@Mapper(componentModel = "spring", uses = {OrderMapper.class, PageableMapper.class})
public interface OrderPageMapper {
	Page<Order> toDomain(PageImpl<OrderEntity> page);
}
