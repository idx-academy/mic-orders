package com.academy.orders.apirest.products.mapper;

import com.academy.orders.domain.product.entity.ProductTranslation;
import com.academy.orders.domain.product.entity.Tag;
import java.util.List;
import java.util.Set;
import org.mapstruct.Named;

public interface ProductMapper {
	@Named("mapProductName")
	default String mapProductName(Set<ProductTranslation> productTranslations) {
		if (productTranslations != null && !productTranslations.isEmpty()) {
			return productTranslations.iterator().next().name();
		}
		return null;
	}

	@Named("mapProductDescription")
	default String mapProductDescription(Set<ProductTranslation> productTranslations) {
		if (productTranslations != null && !productTranslations.isEmpty()) {
			return productTranslations.iterator().next().description();
		}
		return null;
	}

	@Named("mapTags")
	default List<String> mapTags(Set<Tag> tags) {
		return tags.stream().map(Tag::name).toList();
	}

}