package com.academy.orders.application;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.entity.enumerated.Role;
import com.academy.orders.domain.account.entity.enumerated.UserStatus;
import com.academy.orders.domain.cart.dto.CartItemDto;
import com.academy.orders.domain.cart.dto.CartResponseDto;
import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.order.dto.CreateOrderDto;
import com.academy.orders.domain.order.dto.OrderStatusInfo;
import com.academy.orders.domain.order.dto.OrdersFilterParametersDto;
import com.academy.orders.domain.order.dto.UpdateOrderStatusDto;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.OrderItem;
import com.academy.orders.domain.order.entity.OrderManagement;
import com.academy.orders.domain.order.entity.OrderReceiver;
import com.academy.orders.domain.order.entity.PostAddress;
import com.academy.orders.domain.order.entity.enumerated.DeliveryMethod;
import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders.domain.product.dto.ProductRequestDto;
import com.academy.orders.domain.product.dto.ProductManagementFilterDto;
import com.academy.orders.domain.product.dto.ProductTranslationDto;
import com.academy.orders.domain.product.entity.Language;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.ProductTranslation;
import com.academy.orders.domain.product.entity.ProductTranslationManagement;
import com.academy.orders.domain.product.entity.Tag;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import static com.academy.orders.application.TestConstants.IMAGE_URL;
import static com.academy.orders.application.TestConstants.LANGUAGE_EN;
import static com.academy.orders.application.TestConstants.LANGUAGE_UK;
import static com.academy.orders.application.TestConstants.PRODUCT_DESCRIPTION;
import static com.academy.orders.application.TestConstants.PRODUCT_NAME;
import static com.academy.orders.application.TestConstants.TAG_NAME;
import static com.academy.orders.application.TestConstants.TEST_ID;
import static com.academy.orders.application.TestConstants.TEST_PRICE;
import static com.academy.orders.application.TestConstants.TEST_QUANTITY;
import static com.academy.orders.application.TestConstants.TEST_UUID;
import static com.academy.orders.domain.order.entity.enumerated.DeliveryMethod.NOVA;

public class ModelUtils {
	private static final LocalDateTime DATE_TIME = LocalDateTime.of(1, 1, 1, 1, 1, 1);

	public static Product getProduct() {
		return Product.builder().id(TEST_UUID).status(ProductStatus.VISIBLE).image(IMAGE_URL).quantity(TEST_QUANTITY)
				.price(TEST_PRICE).tags(Set.of(getTag())).productTranslations(Set.of(getProductTranslation())).build();
	}

	public static Tag getTag() {
		return Tag.builder().id(TEST_ID).name(TAG_NAME).build();
	}

	public static Language getLanguage() {
		return Language.builder().id(TEST_ID).code(LANGUAGE_UK).build();
	}

	public static Language getLanguageEn() {
		return Language.builder().id(TEST_ID).code(LANGUAGE_EN).build();
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

	public static Pageable getPageable() {
		return getPageable(0, 8, List.of("id"));
	}

	public static Pageable getPageable(Integer page, Integer size, List<String> sort) {
		return Pageable.builder().page(page).size(size).sort(sort).build();
	}

	@SafeVarargs
	public static <T> Page<T> getPageOf(T... elements) {
		return Page.<T>builder().content(List.of(elements)).empty(false).first(true).last(false).number(1)
				.numberOfElements(10).totalPages(10).totalElements(100L).size(1).build();
	}

	public static Order getOrder() {
		return Order.builder().id(TEST_UUID).createdAt(DATE_TIME).isPaid(false).orderStatus(OrderStatus.IN_PROGRESS)
				.postAddress(PostAddress.builder().city("Kyiv").deliveryMethod(NOVA).department("1").build())
				.receiver(OrderReceiver.builder().firstName("John").lastName("Doe").email("test@mail.com").build())
				.orderItems(List.of(getOrderItem())).editedAt(DATE_TIME).total(BigDecimal.valueOf(200)).build();
	}

	public static OrderManagement getOrderManagementForManager() {
		return OrderManagement.builder().id(TEST_UUID).createdAt(DATE_TIME).isPaid(false)
				.orderStatus(OrderStatus.IN_PROGRESS)
				.availableStatuses(List.of(OrderStatus.SHIPPED, OrderStatus.DELIVERED, OrderStatus.COMPLETED,
						OrderStatus.CANCELED))
				.postAddress(PostAddress.builder().city("Kyiv").deliveryMethod(NOVA).department("1").build())
				.receiver(OrderReceiver.builder().firstName("John").lastName("Doe").email("test@mail.com").build())
				.orderItems(List.of(getOrderItem())).editedAt(DATE_TIME).total(BigDecimal.valueOf(200)).build();
	}

	public static OrderManagement getOrderManagementForAdmin() {
		return OrderManagement.builder().id(TEST_UUID).createdAt(DATE_TIME).isPaid(false)
				.orderStatus(OrderStatus.IN_PROGRESS)
				.availableStatuses(List.of(OrderStatus.IN_PROGRESS, OrderStatus.SHIPPED, OrderStatus.DELIVERED,
						OrderStatus.COMPLETED, OrderStatus.CANCELED))
				.postAddress(PostAddress.builder().city("Kyiv").deliveryMethod(NOVA).department("1").build())
				.receiver(OrderReceiver.builder().firstName("John").lastName("Doe").email("test@mail.com").build())
				.orderItems(List.of(getOrderItem())).editedAt(DATE_TIME).total(BigDecimal.valueOf(200)).build();
	}

	public static Order getPaidOrder() {
		return Order.builder().id(TEST_UUID).createdAt(DATE_TIME).isPaid(true).orderStatus(OrderStatus.IN_PROGRESS)
				.postAddress(PostAddress.builder().city("Kyiv").deliveryMethod(NOVA).department("1").build())
				.receiver(OrderReceiver.builder().firstName("John").lastName("Doe").email("test@mail.com").build())
				.orderItems(List.of(getOrderItem())).editedAt(DATE_TIME).total(BigDecimal.valueOf(200)).build();
	}

	public static Order getCanceledOrder() {
		return Order.builder().id(TEST_UUID).createdAt(DATE_TIME).isPaid(false).orderStatus(OrderStatus.CANCELED)
				.postAddress(PostAddress.builder().city("Kyiv").deliveryMethod(NOVA).department("1").build())
				.receiver(OrderReceiver.builder().firstName("John").lastName("Doe").email("test@mail.com").build())
				.orderItems(List.of(getOrderItem())).editedAt(DATE_TIME).total(BigDecimal.valueOf(200)).build();
	}

	public static Order getDeliveredOrder() {
		return Order.builder().id(TEST_UUID).createdAt(DATE_TIME).isPaid(false).orderStatus(OrderStatus.DELIVERED)
				.postAddress(PostAddress.builder().city("Kyiv").deliveryMethod(NOVA).department("1").build())
				.receiver(OrderReceiver.builder().firstName("John").lastName("Doe").email("test@mail.com").build())
				.orderItems(List.of(getOrderItem())).editedAt(DATE_TIME).total(BigDecimal.valueOf(200)).build();
	}

	public static Order getOrderWithoutTotal() {
		return Order.builder().id(TEST_UUID).createdAt(DATE_TIME).isPaid(false).orderStatus(OrderStatus.IN_PROGRESS)
				.postAddress(PostAddress.builder().city("Kyiv").deliveryMethod(NOVA).department("1").build())
				.receiver(OrderReceiver.builder().firstName("John").lastName("Doe").email("test@mail.com").build())
				.orderItems(List.of(getOrderItem())).editedAt(DATE_TIME).build();
	}

	public static OrderItem getOrderItem() {
		return OrderItem.builder().product(getProduct()).quantity(3).price(BigDecimal.valueOf(200)).build();
	}

	public static CartItemDto getCartItemDto(CartItem cartItem, BigDecimal cartPrice) {
		return CartItemDto.builder().productId(cartItem.product().id()).image(cartItem.product().image())
				.name(cartItem.product().productTranslations().iterator().next().name())
				.productPrice(cartItem.product().price()).quantity(cartItem.quantity()).calculatedPrice(cartPrice)
				.build();
	}

	public static CartResponseDto getCartResponseDto(List<CartItemDto> cartItemDtos, BigDecimal totalPrice) {
		return CartResponseDto.builder().items(cartItemDtos).totalPrice(totalPrice).build();
	}

	public static OrdersFilterParametersDto getOrdersFilterParametersDto() {
		return OrdersFilterParametersDto.builder().deliveryMethods(List.of(DeliveryMethod.NOVA))
				.statuses(List.of(OrderStatus.IN_PROGRESS)).isPaid(false).createdBefore(DATE_TIME)
				.createdAfter(DATE_TIME).totalMore(BigDecimal.ZERO).totalLess(BigDecimal.TEN).build();
	}

	public static Product getProductWithQuantity(int quantity) {
		return Product.builder().id(TEST_UUID).status(ProductStatus.VISIBLE).image(IMAGE_URL).quantity(quantity)
				.price(TEST_PRICE).tags(Set.of(getTag())).productTranslations(Set.of(getProductTranslation())).build();
	}

	public static CartItem getCartItem(Product product, int quantity) {
		return CartItem.builder().product(product).quantity(quantity).build();
	}

	public static ProductTranslationManagement getProductTranslationManagement() {
		return ProductTranslationManagement.builder().productId(TEST_UUID).languageId(TEST_ID).name("Name")
				.description("Description").language(getLanguageEn()).build();
	}

	public static ProductManagementFilterDto getManagementFilterDto() {
		return ProductManagementFilterDto.builder().status(ProductStatus.VISIBLE).createdBefore(DATE_TIME)
				.createdAfter(DATE_TIME).priceMore(BigDecimal.ZERO).priceLess(BigDecimal.TEN).build();
	}

	public static ProductRequestDto getProductRequestDto() {
		return ProductRequestDto.builder().status(String.valueOf(ProductStatus.VISIBLE)).image(IMAGE_URL)
				.quantity(TEST_QUANTITY).price(TEST_PRICE).tagIds(List.of(1L))
				.productTranslations(Set.of(ProductTranslationDto.builder().name("Name").description("Description")
						.languageCode("en").build()))
				.build();
	}

	public static ProductRequestDto getProductRequestWithEmptyTagsDto() {
		return ProductRequestDto.builder().status(String.valueOf(ProductStatus.VISIBLE)).image(IMAGE_URL)
				.quantity(TEST_QUANTITY).price(TEST_PRICE).tagIds(Collections.emptyList())
				.productTranslations(Set.of(ProductTranslationDto.builder().name("Name").description("Description")
						.languageCode("en").build()))
				.build();
	}

	public static ProductRequestDto getProductRequestRemoveAllTagsDto() {
		return ProductRequestDto.builder().status(String.valueOf(ProductStatus.VISIBLE)).image(IMAGE_URL)
				.quantity(TEST_QUANTITY).price(TEST_PRICE).tagIds(List.of(-1L))
				.productTranslations(Set.of(ProductTranslationDto.builder().name("Name").description("Description")
						.languageCode("en").build()))
				.build();
	}

	public static ProductRequestDto getProductRequestWithDifferentIds() {
		return ProductRequestDto.builder().status(String.valueOf(ProductStatus.VISIBLE)).image(IMAGE_URL)
				.quantity(TEST_QUANTITY).price(TEST_PRICE).tagIds(List.of(-1L, 1L))
				.productTranslations(Set.of(ProductTranslationDto.builder().name("Name").description("Description")
						.languageCode("en").build()))
				.build();
	}

	public static ProductRequestDto getEmptyProductRequestDto() {
		return ProductRequestDto.builder().tagIds(List.of(1L))
				.productTranslations(Set.of(ProductTranslationDto.builder().languageCode("en").build())).build();
	}

	public static ProductRequestDto getProductRequestDtoWithInvalidLanguageCode() {
		return ProductRequestDto.builder().status(String.valueOf(ProductStatus.VISIBLE)).image(IMAGE_URL)
				.quantity(TEST_QUANTITY).price(TEST_PRICE).tagIds(List.of(1L))
				.productTranslations(Set.of(ProductTranslationDto.builder().name("Name").description("Description")
						.languageCode("invalid").build()))
				.build();
	}

	public static UpdateOrderStatusDto getUpdateOrderStatusDto() {
		return UpdateOrderStatusDto.builder().status(OrderStatus.IN_PROGRESS).isPaid(false).build();
	}

	public static UpdateOrderStatusDto getUpdateOrderStatusDtoWithNullIsPaid() {
		return UpdateOrderStatusDto.builder().status(OrderStatus.IN_PROGRESS).build();
	}

	public static UpdateOrderStatusDto getUpdateOrderStatusDtoWithNullIsPaidAndStatusCompleted() {
		return UpdateOrderStatusDto.builder().status(OrderStatus.COMPLETED).build();
	}

	public static UpdateOrderStatusDto getUpdateOrderStatusDtoWithCompletedStatus() {
		return UpdateOrderStatusDto.builder().status(OrderStatus.COMPLETED).isPaid(false).build();
	}

	public static OrderStatusInfo getOrderStatusInfo() {
		return OrderStatusInfo.builder().availableStatuses(
				List.of(OrderStatus.SHIPPED, OrderStatus.DELIVERED, OrderStatus.COMPLETED, OrderStatus.CANCELED))
				.isPaid(false).build();
	}

	public static Pageable getPageableWithSort() {
		return Pageable.builder().page(0).size(10).sort(List.of("email,asc")).build();
	}

	public static Pageable getDefaultPageable() {
		return Pageable.builder().page(0).size(10).sort(Collections.emptyList()).build();
	}
}
