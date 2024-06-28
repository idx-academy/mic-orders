package com.academy.orders.apirest.orders.mapper;

import com.academy.orders.apirest.products.mapper.ProductPreviewDTOMapper;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.product.entity.Tag;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;
import com.academy.orders_api_rest.generated.model.PageOrderDTO;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProductPreviewDTOMapper.class)
public interface PageOrderDTOMapper {
	PageOrderDTO toDto(Page<Order> orderPage);

	default OffsetDateTime map(LocalDateTime value) {
		return OffsetDateTime.of(value, ZoneOffset.UTC);
	}
}
