package com.academy.orders.infrastructure.order;

import com.academy.orders.infrastructure.order.entity.OrderEntity;
import com.academy.orders.domain.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

	Order fromEntity(OrderEntity orderEntity);
}
