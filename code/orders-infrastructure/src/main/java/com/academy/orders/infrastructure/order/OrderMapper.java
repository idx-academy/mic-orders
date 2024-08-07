package com.academy.orders.infrastructure.order;

import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.OrderItem;
import com.academy.orders.domain.order.entity.OrderReceiver;
import com.academy.orders.domain.order.entity.PostAddress;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.infrastructure.account.AccountMapper;
import com.academy.orders.infrastructure.order.entity.OrderEntity;
import com.academy.orders.infrastructure.order.entity.OrderItemEntity;
import com.academy.orders.infrastructure.order.entity.OrderReceiverVO;
import com.academy.orders.infrastructure.order.entity.PostAddressEntity;
import com.academy.orders.infrastructure.product.entity.ProductEntity;
import java.util.List;
import org.hibernate.Hibernate;
import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AccountMapper.class})
public interface OrderMapper {
	Order fromEntity(OrderEntity orderEntity);
	OrderEntity toEntity(Order order);
	OrderReceiverVO toOrderReceiverVO(OrderReceiver orderReceiver);
	PostAddressEntity toPostAddressEntity(PostAddress postAddress);
	List<OrderItemEntity> toOrderItemEntities(List<OrderItem> orderItem);

	@Mapping(target = "product", source = "product")
	@Mapping(target = "price", source = "orderItem.price")
	@Mapping(target = "quantity", source = "orderItem.quantity")
	OrderItem mapOrderItemWithUpdatedProduct(OrderItem orderItem, Product product);

	@Mapping(target = "orderItems", source = "orderItems")
	Order toOrderWithItems(Order order, List<OrderItem> orderItems);

	@Condition
	default boolean isNotLazyLoaded(ProductEntity source) {
		return Hibernate.isInitialized(source);
	}
}
