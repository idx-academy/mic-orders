package com.academy.orders.application;

import com.academy.orders.domain.product.entity.Language;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.ProductTranslation;
import com.academy.orders.domain.product.entity.Tag;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;

import java.util.Set;

import static com.academy.orders.application.TestConstants.IMAGE_URL;
import static com.academy.orders.application.TestConstants.LANGUAGE_UA;
import static com.academy.orders.application.TestConstants.PRODUCT_DESCRIPTION;
import static com.academy.orders.application.TestConstants.PRODUCT_NAME;
import static com.academy.orders.application.TestConstants.TAG_NAME;
import static com.academy.orders.application.TestConstants.TEST_ID;
import static com.academy.orders.application.TestConstants.TEST_PRICE;
import static com.academy.orders.application.TestConstants.TEST_QUANTITY;
import static com.academy.orders.application.TestConstants.TEST_UUID;

public class ModelUtils {
	public static Product getProduct() {
		return Product.builder().id(TEST_UUID).status(ProductStatus.AVAILABLE).image(IMAGE_URL).quantity(TEST_QUANTITY)
				.price(TEST_PRICE).tags(Set.of(getTag())).productTranslations(Set.of(getProductTranslation())).build();
	}

	public static Tag getTag() {
		return Tag.builder().id(TEST_ID).name(TAG_NAME).build();
	}

	public static Language getLanguage() {
		return Language.builder().id(TEST_ID).code(LANGUAGE_UA).build();
	}

	public static ProductTranslation getProductTranslation() {
		return ProductTranslation.builder().name(PRODUCT_NAME).description(PRODUCT_DESCRIPTION).language(getLanguage())
				.build();
	}
}
