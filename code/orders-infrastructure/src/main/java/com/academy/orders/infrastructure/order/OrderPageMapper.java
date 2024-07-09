package com.academy.orders.infrastructure.order;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.infrastructure.order.entity.OrderEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface OrderPageMapper {
	Page<Order> toDomain(PageImpl<OrderEntity> page);

	default List<String> map(Sort value) {
		return value.stream().map(o -> o.getProperty() + " " + o.getDirection()).toList();
	}
}
