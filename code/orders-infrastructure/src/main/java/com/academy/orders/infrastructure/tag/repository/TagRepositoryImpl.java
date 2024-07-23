package com.academy.orders.infrastructure.tag.repository;

import com.academy.orders.domain.product.entity.Tag;
import com.academy.orders.domain.tag.repository.TagRepository;
import com.academy.orders.infrastructure.tag.TagMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TagRepositoryImpl implements TagRepository {
	private final TagJpaAdapter tagJpaAdapter;
	private final TagMapper tagMapper;

	@Override
	public Set<Tag> getTagsByIds(List<Long> tagIds) {
		return tagMapper.fromEntities(new HashSet<>(tagJpaAdapter.findAllById(tagIds)));
	}
}
