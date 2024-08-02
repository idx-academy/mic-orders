package com.academy.orders.infrastructure.tag;

import com.academy.orders.domain.product.entity.Tag;
import com.academy.orders.infrastructure.tag.entity.TagEntity;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface TagMapper {
	Tag fromEntity(TagEntity tagEntity);
	Set<Tag> fromEntities(Set<TagEntity> tagEntities);
}
