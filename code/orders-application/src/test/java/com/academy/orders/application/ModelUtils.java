package com.academy.orders.application;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.entity.enumerated.Role;
import com.academy.orders.domain.account.entity.enumerated.UserStatus;
import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.order.dto.CreateOrderDto;
import com.academy.orders.domain.order.entity.enumerated.DeliveryMethod;
import com.academy.orders.domain.product.entity.Language;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.ProductTranslation;
import com.academy.orders.domain.product.entity.Tag;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import java.time.LocalDateTime;
import java.util.List;
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
	private static final LocalDateTime DATE_TIME = LocalDateTime.of(1, 1, 1, 1, 1, 1);

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

	public static Account getAccount() {
		return Account.builder().id(1L).email("user@mail.com").firstName("first").lastName("last")
				.password("$2a$12$j6tAmpJpMhU6ATtgRIS0puHsPVxs2upwoBUbTtakSt9tlZ6uZ04IC").role(Role.ROLE_ADMIN)
				.status(UserStatus.ACTIVE).createdAt(DATE_TIME).build();
	}

	public static CreateOrderDto getCreateOrderDto() {
		return CreateOrderDto.builder().firstName("mockFirstName").lastName("mockLastName").email("mockmail@mail.com")
				.city("mockCity").department("mockDepartment").deliveryMethod(DeliveryMethod.NOVA).build();
	}

	public static CartItem getCartItem() {
		return CartItem.builder().product(getProduct()).quantity(1).build();
	}

	public static <T> Page<T> getPage(List<T> content, long totalElements, int totalPages, int number, int size) {
		return Page.<T>builder().totalElements(totalElements).totalPages(totalPages).first(number == 0)
				.last(number == totalPages - 1).number(number).numberOfElements(content.size()).size(size)
				.empty(content.isEmpty()).content(content).build();
	}
}
