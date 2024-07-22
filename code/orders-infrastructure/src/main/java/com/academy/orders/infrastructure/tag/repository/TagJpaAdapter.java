package com.academy.orders.infrastructure.tag.repository;

import com.academy.orders.infrastructure.tag.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagJpaAdapter extends JpaRepository<TagEntity, Long> {
}
