package com.academy.orders.apirest.cart.mapper;

import com.academy.orders.apirest.products.mapper.ProductPreviewDTOMapper;
import com.academy.orders.domain.cart.dto.CartItemDto;
import com.academy.orders.domain.cart.dto.CartResponseDto;
import com.academy.orders.domain.cart.dto.UpdatedCartItemDto;
import com.academy.orders_api_rest.generated.model.CartItemDTO;
import com.academy.orders_api_rest.generated.model.CartItemsResponseDTO;
import java.util.List;

import com.academy.orders_api_rest.generated.model.UpdatedCartItemDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductPreviewDTOMapper.class})
public interface CartItemDTOMapper {
	List<CartItemDTO> toCartItemsResponseDTO(List<CartItemDto> responseDto);
	CartItemsResponseDTO toCartItemsResponseDTO(CartResponseDto responseDto);
	UpdatedCartItemDTO toUpdatedCartItemDTO(UpdatedCartItemDto responseDto);
}
