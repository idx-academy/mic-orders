package com.academy.orders.domain.tag.repository;

import com.academy.orders.domain.product.entity.Tag;

import java.util.List;
import java.util.Set;

/**
 * Repository interface for managing and loading tags by tag ids.
 */
public interface TagRepository {
	/**
	 * Retrieves a set of tags based on the provided list of tag IDs.
	 *
	 * @param tagIds
	 *            the list of tag IDs to retrieve the tags for.
	 * @return a set of {@link Tag} entities that match the provided IDs.
	 *
	 * @author Anton Bondar
	 */
	Set<Tag> getTagsByIds(List<Long> tagIds);
}
