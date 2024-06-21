package com.academy.orders.infrastructure.product;

import com.academy.orders.domain.product.entity.Tag;
import com.academy.orders.infrastructure.tag.entity.TagEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
	Tag fromEntity(TagEntity tagEntity);
}
