package com.academy.orders.infrastructure.order;

import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.OrderItem;
import com.academy.orders.domain.order.entity.OrderReceiver;
import com.academy.orders.domain.order.entity.PostAddress;
import com.academy.orders.infrastructure.order.entity.OrderEntity;
import com.academy.orders.infrastructure.order.entity.OrderItemEntity;
import com.academy.orders.infrastructure.order.entity.OrderReceiverVO;
import com.academy.orders.infrastructure.order.entity.PostAddressEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
	Order fromEntity(OrderEntity orderEntity);
	OrderEntity toEntity(Order order);
	OrderReceiverVO toOrderReceiverVO(OrderReceiver orderReceiver);
	PostAddressEntity toPostAddressEntity(PostAddress postAddress);
	List<OrderItemEntity> toOrderItemEntities(List<OrderItem> orderItem);
}
