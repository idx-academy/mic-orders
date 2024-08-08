package com.academy.orders.boot.infrastructure.tag.repository;

import com.academy.orders.boot.infrastructure.common.repository.AbstractRepository;
import com.academy.orders.domain.product.entity.Tag;
import com.academy.orders.domain.tag.repository.TagRepository;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagRepositoryIT extends AbstractRepository {
    @Autowired
    private TagRepository tagRepository;

    @Test
    void getTagsByIdsTest() {
        List<Long> tagList = List.of(1L, 2L);
        Set<Tag> tagsByIds = tagRepository.getTagsByIds(tagList);

        assertNotNull(tagsByIds);
        assertFalse(tagsByIds.isEmpty());
        tagsByIds.forEach(tag -> assertTrue(tagList.stream().anyMatch(id-> Objects.equals(id, tag.id()))));
    }

    @Test
    void getTagsByIdsWithEmptyListTest() {
        List<Long> tagList = List.of();
        Set<Tag> tagsByIds = tagRepository.getTagsByIds(tagList);

        assertNotNull(tagsByIds);
        assertTrue(tagsByIds.isEmpty());
    }

    @Test
    void getTagsByIdsWithNoResponseTest() {
        List<Long> tagList = List.of(-1L);
        Set<Tag> tagsByIds = tagRepository.getTagsByIds(tagList);

        assertNotNull(tagsByIds);
        assertTrue(tagsByIds.isEmpty());
    }
}
