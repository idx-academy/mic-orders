package com.academy.orders.apirest;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.product.entity.Language;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.ProductTranslation;
import com.academy.orders.domain.product.entity.Tag;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders_api_rest.generated.model.PageProductsDTO;
import com.academy.orders_api_rest.generated.model.PageableDTO;
import com.academy.orders_api_rest.generated.model.ProductPreviewDTO;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import static com.academy.orders.apirest.TestConstants.IMAGE_URL;
import static com.academy.orders.apirest.TestConstants.LANGUAGE_UA;
import static com.academy.orders.apirest.TestConstants.PRODUCT_DESCRIPTION;
import static com.academy.orders.apirest.TestConstants.PRODUCT_NAME;
import static com.academy.orders.apirest.TestConstants.TAG_NAME;
import static com.academy.orders.apirest.TestConstants.TEST_FLOAT_PRICE;
import static com.academy.orders.apirest.TestConstants.TEST_ID;
import static com.academy.orders.apirest.TestConstants.TEST_PRICE;
import static com.academy.orders.apirest.TestConstants.TEST_QUANTITY;
import static com.academy.orders.apirest.TestConstants.TEST_UUID;

public class ModelUtils {
	public static Product getProduct() {
		return Product.builder().id(TEST_UUID).status(ProductStatus.AVAILABLE).image(IMAGE_URL)
				.createdAt(LocalDateTime.of(1, 1, 1, 1, 1)).quantity(TEST_QUANTITY).price(TEST_PRICE)
				.tags(Set.of(getTag())).productTranslations(Set.of(getProductTranslation())).build();
	}

	public static Page<Product> getProductsPage() {
		List<Product> productList = List.of(Product.builder().id(TEST_UUID).status(ProductStatus.AVAILABLE)
				.image(IMAGE_URL).createdAt(LocalDateTime.of(1, 1, 1, 1, 1)).quantity(TEST_QUANTITY).price(TEST_PRICE)
				.tags(Set.of(getTag())).productTranslations(Set.of(getProductTranslation())).build());

		return new Page<>(1L, 1, true, true, 1, productList.size(), productList.size(), false, productList);
	}

	public static Product getProductWithEmptyTranslations() {
		return Product.builder().id(TEST_UUID).status(ProductStatus.AVAILABLE).image(IMAGE_URL)
				.createdAt(LocalDateTime.of(1, 1, 1, 1, 1)).quantity(TEST_QUANTITY).price(TEST_PRICE)
				.tags(Set.of(getTag())).productTranslations(Collections.emptySet()).build();
	}

	public static Product getProductWithNullTranslations() {
		return Product.builder().id(TEST_UUID).status(ProductStatus.AVAILABLE).image(IMAGE_URL)
				.createdAt(LocalDateTime.of(1, 1, 1, 1, 1)).quantity(TEST_QUANTITY).price(TEST_PRICE)
				.tags(Set.of(getTag())).productTranslations(null).build();
	}

	public static Product getProductWithEmptyTags() {
		return Product.builder().id(TEST_UUID).status(ProductStatus.AVAILABLE).image(IMAGE_URL)
				.createdAt(LocalDateTime.of(1, 1, 1, 1, 1)).quantity(TEST_QUANTITY).price(TEST_PRICE)
				.tags(Collections.emptySet()).productTranslations(Set.of(getProductTranslation())).build();
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

	public static ProductPreviewDTO getProductPreviewDTO() {
		var productDTO = new ProductPreviewDTO();

		productDTO.setId(String.valueOf(TEST_UUID));
		productDTO.setImage(IMAGE_URL);
		productDTO.setName(PRODUCT_NAME);
		productDTO.setDescription(PRODUCT_DESCRIPTION);
		productDTO.setPrice(TEST_FLOAT_PRICE);
		productDTO.setTags(List.of(TAG_NAME));
		productDTO.setStatus(ProductPreviewDTO.StatusEnum.AVAILABLE);

		return productDTO;
	}

	public static Pageable getPageable() {
		return Pageable.builder().page(1).size(1).sort(List.of("price, asc")).build();
	}

	public static PageableDTO getPageableDTO() {
		return new PageableDTO().page(0).size(8);
	}

	public static PageProductsDTO getPageProductsDTO() {
		var productDTO = new PageProductsDTO();

		productDTO.setTotalElements(1L);
		productDTO.setTotalPages(1);
		productDTO.setFirst(true);
		productDTO.setLast(false);
		productDTO.setNumber(1);
		productDTO.setNumberOfElements(1);
		productDTO.setSize(1);
		productDTO.setEmpty(false);
		productDTO.addContentItem(getProductPreviewDTO());

		return productDTO;

	}
}