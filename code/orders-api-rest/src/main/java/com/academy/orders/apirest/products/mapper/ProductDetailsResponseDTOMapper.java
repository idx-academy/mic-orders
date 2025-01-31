package com.academy.orders.apirest.products.mapper;

import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.Tag;
import com.academy.orders.domain.product.entity.ProductTranslation;
import com.academy.orders_api_rest.generated.model.ProductDetailsResponseDTO;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ProductDetailsResponseDTOMapper {
	@Mapping(source = "productTranslations", target = "name", qualifiedByName = "mapName")
	@Mapping(source = "productTranslations", target = "description", qualifiedByName = "mapDescription")
	@Mapping(source = "image", target = "image")
	@Mapping(source = "tags", target = "tags", qualifiedByName = "mapTags")
	@Mapping(source = "quantity", target = "quantity")
	@Mapping(source = "discount.amount", target = "discountedAmount")
	ProductDetailsResponseDTO toDTO(Product product);

	@AfterMapping
	default void defineDiscountRules(@MappingTarget Product.ProductBuilder builder, Product productEntity) {
		if (productEntity.getDiscount() != null) {
			builder.originalPrice(productEntity.getPrice());
			builder.price(Product.calculatePrice(productEntity.getPrice(), productEntity.getDiscount().getAmount()));
		}
	}

	@Named("mapName")
	default String mapName(Set<ProductTranslation> translations) {
		return translations.stream().findFirst().map(ProductTranslation::name).orElse(null);
	}

	@Named("mapDescription")
	default String mapDescription(Set<ProductTranslation> translations) {
		return translations.stream().findFirst().map(ProductTranslation::description).orElse(null);
	}

	@Named("mapTags")
	default List<String> mapTags(Set<Tag> tags) {
		return tags.stream().map(Tag::name).toList();
	}
}
