package com.academy.orders.apirest.orders.mapper;

import com.academy.orders.apirest.common.mapper.LocalDateTimeMapper;
import com.academy.orders.apirest.products.mapper.ProductPreviewDTOMapper;
import com.academy.orders.domain.order.dto.CreateOrderDto;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.OrderItem;
import com.academy.orders.domain.product.usecase.CalculatePriceUseCase;
import com.academy.orders_api_rest.generated.model.OrderDTO;
import com.academy.orders_api_rest.generated.model.PlaceOrderRequestDTO;
import java.math.BigDecimal;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {ProductPreviewDTOMapper.class, LocalDateTimeMapper.class})
public abstract class OrderDTOMapper {
	@Autowired
	protected CalculatePriceUseCase calculatePriceUseCase;

	@Mapping(target = "total", expression = "java(calculatePriceUseCase.calculateTotalPrice(order.orderItems()))")
	public abstract OrderDTO toDto(Order order);

	public abstract CreateOrderDto toCreateOrderDto(PlaceOrderRequestDTO placeOrderRequestDTO);
}
