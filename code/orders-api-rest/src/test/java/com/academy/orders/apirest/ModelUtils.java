package com.academy.orders.apirest;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.OrderItem;
import com.academy.orders.domain.order.entity.OrderReceiver;
import com.academy.orders.domain.order.entity.PostAddress;
import com.academy.orders.domain.order.entity.enumerated.DeliveryMethod;
import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders.domain.product.entity.Language;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.ProductTranslation;
import com.academy.orders.domain.product.entity.Tag;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders_api_rest.generated.model.CartItemDTO;
import com.academy.orders_api_rest.generated.model.CartItemsResponseDTO;
import com.academy.orders_api_rest.generated.model.UserOrderDTO;
import com.academy.orders_api_rest.generated.model.OrderItemDTO;
import com.academy.orders_api_rest.generated.model.OrderReceiverDTO;
import com.academy.orders_api_rest.generated.model.OrderStatusDTO;
import com.academy.orders_api_rest.generated.model.PageUserOrderDTO;
import com.academy.orders_api_rest.generated.model.PageableDTO;
import com.academy.orders_api_rest.generated.model.PlaceOrderRequestDTO;
import com.academy.orders_api_rest.generated.model.PostAddressDTO;
import com.academy.orders_api_rest.generated.model.ProductPreviewDTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.academy.orders.apirest.TestConstants.IMAGE_URL;
import static com.academy.orders.apirest.TestConstants.LANGUAGE_UA;
import static com.academy.orders.apirest.TestConstants.PRODUCT_DESCRIPTION;
import static com.academy.orders.apirest.TestConstants.PRODUCT_NAME;
import static com.academy.orders.apirest.TestConstants.TAG_NAME;
import static com.academy.orders.apirest.TestConstants.TEST_CITY;
import static com.academy.orders.apirest.TestConstants.TEST_DEPARTMENT;
import static com.academy.orders.apirest.TestConstants.TEST_EMAIL;
import static com.academy.orders.apirest.TestConstants.TEST_FIRST_NAME;
import static com.academy.orders.apirest.TestConstants.TEST_FLOAT_PRICE;
import static com.academy.orders.apirest.TestConstants.TEST_ID;
import static com.academy.orders.apirest.TestConstants.TEST_LAST_NAME;
import static com.academy.orders.apirest.TestConstants.TEST_PRICE;
import static com.academy.orders.apirest.TestConstants.TEST_QUANTITY;
import static com.academy.orders.apirest.TestConstants.TEST_UUID;
import static com.academy.orders_api_rest.generated.model.DeliveryMethodDTO.NOVA;
import static java.util.Collections.emptySet;
import static java.util.Collections.singletonList;

public class ModelUtils {
	private static final LocalDateTime DATE_TIME = LocalDateTime.of(1, 1, 1, 1, 1);

	public static Product getProduct() {
		return Product.builder().id(TEST_UUID).status(ProductStatus.AVAILABLE).image(IMAGE_URL).createdAt(DATE_TIME)
				.quantity(TEST_QUANTITY).price(TEST_PRICE).tags(Set.of(getTag()))
				.productTranslations(Set.of(getProductTranslation())).build();
	}

	public static Page<Product> getProductsPage() {
		List<Product> productList = List.of(getProduct());
		return new Page<>(1L, 1, true, true, 1, productList.size(), productList.size(), false, productList);
	}

	public static Product getProductWithEmptyTranslations() {
		return Product.builder().id(TEST_UUID).status(ProductStatus.AVAILABLE).image(IMAGE_URL).createdAt(DATE_TIME)
				.quantity(TEST_QUANTITY).price(TEST_PRICE).tags(Set.of(getTag())).productTranslations(emptySet())
				.build();
	}

	public static Product getProductWithNullTranslations() {
		return Product.builder().id(TEST_UUID).status(ProductStatus.AVAILABLE).image(IMAGE_URL).createdAt(DATE_TIME)
				.quantity(TEST_QUANTITY).price(TEST_PRICE).tags(Set.of(getTag())).productTranslations(null).build();
	}

	public static Product getProductWithEmptyTags() {
		return Product.builder().id(TEST_UUID).status(ProductStatus.AVAILABLE).image(IMAGE_URL).createdAt(DATE_TIME)
				.quantity(TEST_QUANTITY).price(TEST_PRICE).tags(emptySet())
				.productTranslations(Set.of(getProductTranslation())).build();
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

	public static PageableDTO getPageableDTO() {
		return new PageableDTO().page(0).size(8);
	}

	public static PlaceOrderRequestDTO getPlaceOrderRequestDTO() {
		var placeOrderRequestDTO = new PlaceOrderRequestDTO();
		placeOrderRequestDTO.setFirstName(TEST_FIRST_NAME);
		placeOrderRequestDTO.setLastName(TEST_LAST_NAME);
		placeOrderRequestDTO.setEmail(TEST_EMAIL);
		placeOrderRequestDTO.setCity(TEST_CITY);
		placeOrderRequestDTO.setDepartment(TEST_DEPARTMENT);
		placeOrderRequestDTO.setDeliveryMethod(NOVA);

		return placeOrderRequestDTO;
	}

	public static Order getOrder() {
		return Order.builder().id(TEST_UUID).createdAt(LocalDateTime.of(1, 1, 1, 1, 1)).isPaid(false)
				.orderStatus(OrderStatus.IN_PROGRESS)
				.postAddress(PostAddress.builder().city(TEST_CITY).deliveryMethod(DeliveryMethod.NOVA)
						.department(TEST_DEPARTMENT).build())
				.receiver(OrderReceiver.builder().firstName(TEST_FIRST_NAME).lastName(TEST_LAST_NAME).email(TEST_EMAIL)
						.build())
				.orderItems(List.of(getOrderItem())).build();
	}

	public static OrderItem getOrderItem() {
		return OrderItem.builder().product(getProduct()).quantity(3).price(BigDecimal.valueOf(200)).build();
	}

	public static RequestPostProcessor getJwtRequest(Long userId, String... roles) {
		var grantedRoles = Arrays.stream(roles).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toCollection(() -> new ArrayList<GrantedAuthority>()));
		return SecurityMockMvcRequestPostProcessors.jwt().jwt(builder -> builder.claim("id", userId))
				.authorities(grantedRoles);
	}

	@SafeVarargs
	public static <T> Page<T> getPageOf(T... elements) {
		return Page.<T>builder().content(List.of(elements)).empty(false).first(true).last(false).number(1)
				.numberOfElements(10).totalPages(10).totalElements(100L).size(1).build();
	}

	public static PageUserOrderDTO getPageOrderDTO() {
		PageUserOrderDTO pageOrderDTO = new PageUserOrderDTO();
		pageOrderDTO.setEmpty(false);
		pageOrderDTO.setTotalElements(100L);
		pageOrderDTO.setTotalPages(10);
		pageOrderDTO.setFirst(true);
		pageOrderDTO.setLast(false);
		pageOrderDTO.setNumber(1);
		pageOrderDTO.setNumberOfElements(10);
		pageOrderDTO.setSize(10);
		pageOrderDTO.content(List.of(getOrderDTO()));
		return pageOrderDTO;
	}

	public static UserOrderDTO getOrderDTO() {
		UserOrderDTO orderDTO = new UserOrderDTO();
		orderDTO.setId(TEST_UUID);
		orderDTO.setOrderStatus(OrderStatusDTO.IN_PROGRESS);
		orderDTO.setCreatedAt(OffsetDateTime.of(1, 1, 1, 1, 1, 1, 1, ZoneOffset.UTC));
		orderDTO.setIsPaid(false);
		orderDTO.setPostAddress(getPostAddressDTO());
		orderDTO.setReceiver(getOrderReceiverDTO());
		orderDTO.setOrderItems(List.of(getOrderItemDTO()));
		return orderDTO;
	}

	public static OrderItemDTO getOrderItemDTO() {
		OrderItemDTO orderItemDTO = new OrderItemDTO();
		orderItemDTO.setPrice(BigDecimal.valueOf(200));
		orderItemDTO.setQuantity(3);
		orderItemDTO.setProduct(getProductPreviewDTO());
		return orderItemDTO;
	}

	public static OrderReceiverDTO getOrderReceiverDTO() {
		OrderReceiverDTO orderReceiverDTO = new OrderReceiverDTO();
		orderReceiverDTO.email(TEST_EMAIL);
		orderReceiverDTO.firstName(TEST_FIRST_NAME);
		orderReceiverDTO.lastName(TEST_LAST_NAME);
		return orderReceiverDTO;
	}

	public static PostAddressDTO getPostAddressDTO() {
		PostAddressDTO postAddressDTO = new PostAddressDTO();
		postAddressDTO.setCity(TEST_CITY);
		postAddressDTO.setDepartment(TEST_DEPARTMENT);
		postAddressDTO.setDeliveryMethod(NOVA);
		return postAddressDTO;
	}

	public static MultiValueMap<String, String> getPageableParams(Integer page, Integer size, List<String> sort) {
		MultiValueMap<String, String> pageableParams = new LinkedMultiValueMap<>();
		pageableParams.add("page", page.toString());
		pageableParams.add("size", size.toString());
		pageableParams.addAll("sort", sort);
		return pageableParams;
	}

	public static Pageable getPageable() {
		return getPageable(0, 8, List.of("id"));
	}

	public static Pageable getPageable(Integer page, Integer size, List<String> sort) {
		return Pageable.builder().page(page).size(size).sort(sort).build();
	}

	public static CartItemsResponseDTO getCartItemResponseDto() {
		return new CartItemsResponseDTO(singletonList(getCartItemDTO()), TEST_PRICE.doubleValue());
	}

	private static CartItemDTO getCartItemDTO() {
		CartItemDTO cartItemDTO = new CartItemDTO();
		cartItemDTO.setProductId(TEST_UUID);
		cartItemDTO.setQuantity(TEST_QUANTITY);
		cartItemDTO.setProductPrice(TEST_PRICE.doubleValue());
		cartItemDTO.setCalculatedPrice(TEST_PRICE.doubleValue());
		cartItemDTO.setName(PRODUCT_NAME);
		cartItemDTO.setImage(IMAGE_URL);

		return cartItemDTO;
	}
}