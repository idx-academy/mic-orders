package com.academy.orders.infrastructure.tag.repository;

import com.academy.orders.infrastructure.tag.entity.TagEntity;
import org.springframework.data.repository.CrudRepository;

public interface TagJpaAdapter extends CrudRepository<TagEntity, Long> {
}
