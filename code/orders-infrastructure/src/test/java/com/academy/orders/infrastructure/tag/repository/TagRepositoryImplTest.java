package com.academy.orders.infrastructure.tag.repository;

import com.academy.orders.infrastructure.tag.TagMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Set;
import static com.academy.orders.infrastructure.ModelUtils.getTag;
import static com.academy.orders.infrastructure.ModelUtils.getTagEntity;
import static com.academy.orders.infrastructure.TestConstants.TEST_ID;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagRepositoryImplTest {
	@InjectMocks
	private TagRepositoryImpl tagRepository;

	@Mock
	private TagJpaAdapter tagJpaAdapter;

	@Mock
	private TagMapper tagMapper;

	@Test
	void getTagsByIdsTest() {
		when(tagJpaAdapter.findAllById(List.of(TEST_ID))).thenReturn(List.of(getTagEntity()));
		when(tagMapper.fromEntities(Set.of(getTagEntity()))).thenReturn(Set.of(getTag()));

		var result = tagRepository.getTagsByIds(List.of(TEST_ID));
		Assertions.assertEquals(Set.of(getTag()), result);
	}
}
