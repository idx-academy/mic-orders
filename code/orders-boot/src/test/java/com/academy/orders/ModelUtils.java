package com.academy.orders;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.entity.enumerated.Role;
import com.academy.orders.domain.account.entity.enumerated.UserStatus;
import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.OrderItem;
import com.academy.orders.domain.order.entity.OrderReceiver;
import com.academy.orders.domain.order.entity.PostAddress;
import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders.domain.product.dto.ProductManagementFilterDto;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.ProductManagement;
import com.academy.orders.domain.product.entity.ProductTranslationManagement;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.academy.orders.boot.TestConstants.PRODUCT_IMAGE;
import static com.academy.orders.boot.TestConstants.SORT_BY_PRICE_ASC;
import static com.academy.orders.boot.TestConstants.SORT_BY_PRICE_DESC;
import static com.academy.orders.domain.order.entity.enumerated.DeliveryMethod.NOVA;
import static com.academy.orders.domain.product.entity.enumerated.ProductStatus.VISIBLE;
import static java.math.BigDecimal.valueOf;
import static java.util.Collections.emptyList;

public class ModelUtils {
	public static final LocalDateTime DATE_TIME = LocalDateTime.of(1, 1, 1, 1, 1);
	public static final String IMAGE_LINK = "https://j65jb0fdkxuua0go.public.blob.vercel-storage.com/image.jpg";
	public static final String IMAGE_NAME = "image.jpg";

	public static Account getAccount() {
		return Account.builder().id(1L).email("user@mail.com").firstName("first").lastName("last")
				.password("$2a$12$j6tAmpJpMhU6ATtgRIS0puHsPVxs2upwoBUbTtakSt9tlZ6uZ04IC").role(Role.ROLE_ADMIN)
				.status(UserStatus.ACTIVE).createdAt(DATE_TIME).build();
	}

	public static Order getOrderWithoutId() {
		return Order.builder().isPaid(false).orderStatus(OrderStatus.IN_PROGRESS)
				.postAddress(PostAddress.builder().city("Kyiv").deliveryMethod(NOVA).department("1").build())
				.receiver(getOrderReceiver()).orderItems(List.of(getOrderItem())).build();
	}

	public static OrderItem getOrderItem() {
		return OrderItem.builder().product(getProductWithImageName()).quantity(3).price(BigDecimal.valueOf(200))
				.build();
	}

	public static Product getProductWithImageName() {
		return getProductBuilderWithoutImage().image(IMAGE_NAME).build();
	}

	public static Product getProductWithImageLink() {
		return getProductBuilderWithoutImage().image(IMAGE_LINK).build();
	}

	private static Product.ProductBuilder getProductBuilderWithoutImage() {
		return Product.builder().id(UUID.fromString("93063b3f-7c61-47ee-8612-4bfae9f3ff0f"))
				.status(ProductStatus.VISIBLE).createdAt(DATE_TIME).quantity(100).price(BigDecimal.valueOf(100.00));
	}

	public static OrderReceiver getOrderReceiver() {
		return OrderReceiver.builder().email("mock@mail.com").firstName("MockFirst").lastName("MockLast").build();
	}

	public static Pageable getPageableSortAsc() {
		return Pageable.builder().page(0).size(10).sort(List.of(SORT_BY_PRICE_ASC)).build();
	}

	public static Pageable getPageableSortDesc() {
		return Pageable.builder().page(0).size(10).sort(List.of(SORT_BY_PRICE_DESC)).build();
	}

	public static Pageable getPageable() {
		return Pageable.builder().page(0).size(10).sort(Collections.emptyList()).build();
	}

	public static ProductManagementFilterDto getProductManagementFilterDto() {
		return ProductManagementFilterDto.builder().tags(emptyList()).build();
	}

	public static ProductManagementFilterDto getProductManagementFilterDtoWithPrices() {
		return ProductManagementFilterDto.builder().priceMore(valueOf(1000)).priceLess(valueOf(1500)).quantityMore(5)
				.quantityLess(11).status(VISIBLE).tags(emptyList()).build();
	}

	public static ProductManagementFilterDto getProductManagementFilterSearchByName() {
		return ProductManagementFilterDto.builder().searchByName("iphone").tags(emptyList()).build();
	}

	public static ProductManagement getProductManagement() {
		return ProductManagement.builder().id(UUID.fromString("84b7e491-0dcf-44c3-beb6-7496dc6ef3b1")).status(VISIBLE)
				.image(PRODUCT_IMAGE).createdAt(DATE_TIME).quantity(1000).price(BigDecimal.valueOf(559.00))
				.productTranslationManagement(getProductTranslations()).build();
	}

	public static ProductManagement getProductToSave() {
		return ProductManagement.builder().status(VISIBLE).image(PRODUCT_IMAGE).createdAt(DATE_TIME).quantity(1000)
				.price(BigDecimal.valueOf(559.00)).productTranslationManagement(Set.of()).build();
	}

	public static Set<ProductTranslationManagement> getProductTranslations() {
		return Set.of(
				ProductTranslationManagement.builder()
						.productId(UUID.fromString("84b7e491-0dcf-44c3-beb6-7496dc6ef3b1")).languageId(1L)
						.name("New name").description("New description").build(),
				ProductTranslationManagement.builder()
						.productId(UUID.fromString("84b7e491-0dcf-44c3-beb6-7496dc6ef3b1")).languageId(2L)
						.name("Нове ім'я").description("Новий опис").build());
	}

	public static CartItem getCartItem() {
		return CartItem.builder().product(getProductWithImageName()).quantity(10).build();
	}
}
