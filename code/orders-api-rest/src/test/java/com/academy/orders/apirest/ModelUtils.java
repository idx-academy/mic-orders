package com.academy.orders.apirest;

import com.academy.orders.domain.account.dto.AccountManagementFilterDto;
import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.entity.enumerated.Role;
import com.academy.orders.domain.account.entity.enumerated.UserStatus;
import com.academy.orders.domain.cart.dto.UpdatedCartItemDto;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
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
import com.academy.orders.domain.product.dto.ProductManagementFilterDto;
import com.academy.orders.domain.product.dto.ProductRequestDto;
import com.academy.orders.domain.product.dto.ProductTranslationDto;
import com.academy.orders.domain.product.entity.Language;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.ProductTranslation;
import com.academy.orders.domain.product.entity.Tag;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders_api_rest.generated.model.AccountResponseDTO;
import com.academy.orders_api_rest.generated.model.CartItemDTO;
import com.academy.orders_api_rest.generated.model.CartItemsResponseDTO;
import com.academy.orders_api_rest.generated.model.ManagerOrderDTO;
import com.academy.orders_api_rest.generated.model.OrderItemDTO;
import com.academy.orders_api_rest.generated.model.OrderReceiverDTO;
import com.academy.orders_api_rest.generated.model.OrderStatusDTO;
import com.academy.orders_api_rest.generated.model.OrderStatusInfoDTO;
import com.academy.orders_api_rest.generated.model.OrdersFilterParametersDTO;
import com.academy.orders_api_rest.generated.model.PageAccountsDTO;
import com.academy.orders_api_rest.generated.model.PageManagerOrderPreviewDTO;
import com.academy.orders_api_rest.generated.model.PageProductSearchResultDTO;
import com.academy.orders_api_rest.generated.model.PageProductsDTO;
import com.academy.orders_api_rest.generated.model.PageUserOrderDTO;
import com.academy.orders_api_rest.generated.model.PageableDTO;
import com.academy.orders_api_rest.generated.model.PlaceOrderRequestDTO;
import com.academy.orders_api_rest.generated.model.PostAddressDTO;
import com.academy.orders_api_rest.generated.model.ProductDetailsResponseDTO;
import com.academy.orders_api_rest.generated.model.ProductManagementContentDTO;
import com.academy.orders_api_rest.generated.model.ProductManagementPageDTO;
import com.academy.orders_api_rest.generated.model.ProductManagementStatusDTO;
import com.academy.orders_api_rest.generated.model.ProductPreviewDTO;
import com.academy.orders_api_rest.generated.model.ProductRequestDTO;
import com.academy.orders_api_rest.generated.model.ProductResponseDTO;
import com.academy.orders_api_rest.generated.model.ProductSearchResultDTO;
import com.academy.orders_api_rest.generated.model.ProductStatusDTO;
import com.academy.orders_api_rest.generated.model.ProductTranslationDTO;
import com.academy.orders_api_rest.generated.model.TagDTO;
import com.academy.orders_api_rest.generated.model.UpdateOrderStatusRequestDTO;
import com.academy.orders_api_rest.generated.model.UpdatedCartItemDTO;
import com.academy.orders_api_rest.generated.model.UserOrderDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.academy.orders.apirest.TestConstants.IMAGE_URL;
import static com.academy.orders.apirest.TestConstants.LANGUAGE_UK;
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
import static com.academy.orders.apirest.TestConstants.TEST_PASSWORD;
import static com.academy.orders.apirest.TestConstants.TEST_PRICE;
import static com.academy.orders.apirest.TestConstants.TEST_QUANTITY;
import static com.academy.orders.apirest.TestConstants.TEST_UUID;
import static com.academy.orders_api_rest.generated.model.DeliveryMethodDTO.NOVA;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.Collections.singletonList;

public class ModelUtils {
	private static final LocalDateTime DATE_TIME = LocalDateTime.of(1, 1, 1, 1, 1);
	public static final OffsetDateTime OFFSET_DATE_TIME = OffsetDateTime.of(1, 1, 1, 1, 1, 1, 1, ZoneOffset.UTC);

	public static Product getProduct() {
		return Product.builder().id(TEST_UUID).status(ProductStatus.VISIBLE).image(IMAGE_URL).createdAt(DATE_TIME)
				.quantity(TEST_QUANTITY).price(TEST_PRICE).tags(Set.of(getTag()))
				.productTranslations(Set.of(getProductTranslation())).build();
	}

	public static Page<Product> getProductsPage() {
		List<Product> productList = List.of(getProduct());
		return new Page<>(1L, 1, true, true, 1, productList.size(), productList.size(), false, productList);
	}

	public static Product getProductWithEmptyTranslations() {
		return Product.builder().id(TEST_UUID).status(ProductStatus.VISIBLE).image(IMAGE_URL).createdAt(DATE_TIME)
				.quantity(TEST_QUANTITY).price(TEST_PRICE).tags(Set.of(getTag())).productTranslations(emptySet())
				.build();
	}

	public static Product getProductWithNullTranslations() {
		return Product.builder().id(TEST_UUID).status(ProductStatus.VISIBLE).image(IMAGE_URL).createdAt(DATE_TIME)
				.quantity(TEST_QUANTITY).price(TEST_PRICE).tags(Set.of(getTag())).productTranslations(null).build();
	}

	public static Product getProductWithEmptyTags() {
		return Product.builder().id(TEST_UUID).status(ProductStatus.VISIBLE).image(IMAGE_URL).createdAt(DATE_TIME)
				.quantity(TEST_QUANTITY).price(TEST_PRICE).tags(emptySet())
				.productTranslations(Set.of(getProductTranslation())).build();
	}

	public static Tag getTag() {
		return Tag.builder().id(TEST_ID).name(TAG_NAME).build();
	}

	public static Language getLanguage() {
		return Language.builder().id(TEST_ID).code(LANGUAGE_UK).build();
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
		productDTO.setPrice(TEST_PRICE);
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

	public static OrderManagement getOrderManagement() {
		return OrderManagement.builder().id(TEST_UUID).createdAt(LocalDateTime.of(1, 1, 1, 1, 1)).isPaid(false)
				.orderStatus(OrderStatus.IN_PROGRESS)
				.availableStatuses(List.of(OrderStatus.SHIPPED, OrderStatus.DELIVERED, OrderStatus.COMPLETED,
						OrderStatus.CANCELED))
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

	public static PageUserOrderDTO getPageUserOrderDTO() {
		PageUserOrderDTO pageOrderDTO = new PageUserOrderDTO();
		pageOrderDTO.setEmpty(false);
		pageOrderDTO.setTotalElements(100L);
		pageOrderDTO.setTotalPages(10);
		pageOrderDTO.setFirst(true);
		pageOrderDTO.setLast(false);
		pageOrderDTO.setNumber(1);
		pageOrderDTO.setNumberOfElements(10);
		pageOrderDTO.setSize(10);
		pageOrderDTO.content(List.of(getUserOrderDTO()));
		return pageOrderDTO;
	}

	public static OrdersFilterParametersDTO getOrdersFilterParametersDTO() {
		OrdersFilterParametersDTO ordersFilterParametersDTO = new OrdersFilterParametersDTO();
		ordersFilterParametersDTO.addDeliveryMethodsItem(NOVA);
		ordersFilterParametersDTO.addStatusesItem(OrderStatusDTO.IN_PROGRESS);
		ordersFilterParametersDTO.createdBefore(OFFSET_DATE_TIME);
		ordersFilterParametersDTO.createdAfter(OFFSET_DATE_TIME);
		ordersFilterParametersDTO.setIsPaid(false);
		ordersFilterParametersDTO.setTotalLess(BigDecimal.ZERO);
		ordersFilterParametersDTO.setTotalMore(BigDecimal.TEN);
		ordersFilterParametersDTO.setAccountEmail(TEST_EMAIL);
		return ordersFilterParametersDTO;
	}

	public static OrdersFilterParametersDto getOrdersFilterParametersDto() {
		return OrdersFilterParametersDto.builder().deliveryMethods(List.of(DeliveryMethod.NOVA))
				.statuses(List.of(OrderStatus.IN_PROGRESS)).isPaid(false).createdBefore(DATE_TIME)
				.createdAfter(DATE_TIME).totalMore(BigDecimal.ZERO).accountEmail(TEST_EMAIL).totalLess(BigDecimal.TEN)
				.build();
	}

	public static PageManagerOrderPreviewDTO getPageManagerOrderPreviewDTO() {
		PageManagerOrderPreviewDTO pageOrderDTO = new PageManagerOrderPreviewDTO();
		pageOrderDTO.setEmpty(false);
		pageOrderDTO.setTotalElements(100L);
		pageOrderDTO.setTotalPages(10);
		pageOrderDTO.setFirst(true);
		pageOrderDTO.setLast(false);
		pageOrderDTO.setNumber(1);
		pageOrderDTO.setNumberOfElements(10);
		pageOrderDTO.setSize(10);
		return pageOrderDTO;
	}

	public static UserOrderDTO getUserOrderDTO() {
		UserOrderDTO orderDTO = new UserOrderDTO();
		orderDTO.setId(TEST_UUID);
		orderDTO.setOrderStatus(OrderStatusDTO.IN_PROGRESS);
		orderDTO.setCreatedAt(OFFSET_DATE_TIME);
		orderDTO.total(BigDecimal.TEN);
		orderDTO.setIsPaid(false);
		orderDTO.setPostAddress(getPostAddressDTO());
		orderDTO.setReceiver(getOrderReceiverDTO());
		orderDTO.setOrderItems(List.of(getOrderItemDTO()));
		return orderDTO;
	}

	public static ManagerOrderDTO getManagerOrderDTO() {
		ManagerOrderDTO orderDTO = new ManagerOrderDTO();
		orderDTO.setId(TEST_UUID);
		orderDTO.setOrderStatus(OrderStatusDTO.IN_PROGRESS);
		orderDTO.setCreatedAt(OFFSET_DATE_TIME);
		orderDTO.total(BigDecimal.TEN);
		orderDTO.setIsPaid(false);
		orderDTO.editedAt(OFFSET_DATE_TIME);
		orderDTO.account(getAccountResponseDTO());
		orderDTO.setPostAddress(getPostAddressDTO());
		orderDTO.setReceiver(getOrderReceiverDTO());
		orderDTO.setOrderItems(List.of(getOrderItemDTO()));
		return orderDTO;
	}

	private static AccountResponseDTO getAccountResponseDTO() {
		AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
		accountResponseDTO.id(TEST_ID);
		accountResponseDTO.email(TEST_EMAIL);
		accountResponseDTO.firstName(TEST_FIRST_NAME);
		accountResponseDTO.lastName(TEST_LAST_NAME);
		return accountResponseDTO;
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

	public static MultiValueMap<String, String> getOrdersFilterParametersDTOParams(OrdersFilterParametersDTO dto) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		dto.getDeliveryMethods().forEach(deliveryMethod -> params.add("deliveryMethods", deliveryMethod.toString()));
		dto.getStatuses().forEach(status -> params.add("statuses", status.toString()));
		params.add("isPaid", String.valueOf(dto.getIsPaid()));
		params.add("createdBefore", String.valueOf(dto.getCreatedBefore()));
		params.add("createdAfter", String.valueOf(dto.getCreatedAfter()));
		params.add("totalMore", String.valueOf(dto.getTotalMore()));
		params.add("totalLess", String.valueOf(dto.getTotalLess()));
		params.add("accountEmail", String.valueOf(dto.getAccountEmail()));
		return params;
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

	public static UpdatedCartItemDto getUpdatedCartItemDto() {
		return UpdatedCartItemDto.builder().productId(TEST_UUID).quantity(1).productPrice(BigDecimal.valueOf(1))
				.calculatedPrice(BigDecimal.valueOf(1)).totalPrice(BigDecimal.valueOf(1)).build();
	}

	public static UpdatedCartItemDTO getUpdatedCartItemDTO() {
		UpdatedCartItemDTO updatedCartItemDTO = new UpdatedCartItemDTO();
		updatedCartItemDTO.setProductId(TEST_UUID);
		updatedCartItemDTO.setQuantity(1);
		updatedCartItemDTO.setProductPrice(BigDecimal.valueOf(1));
		updatedCartItemDTO.setCalculatedPrice(BigDecimal.valueOf(1));
		updatedCartItemDTO.setTotalPrice(BigDecimal.valueOf(1));
		return updatedCartItemDTO;
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

	public static ProductManagementFilterDto getManagementFilterDto() {
		return ProductManagementFilterDto.builder().status(ProductStatus.VISIBLE).createdBefore(DATE_TIME)
				.createdAfter(DATE_TIME).priceMore(BigDecimal.ZERO).priceLess(BigDecimal.TEN).build();
	}

	public static ProductManagementContentDTO getProductManagementContentDTO() {
		var content = new ProductManagementContentDTO();
		content.setId(TEST_UUID);
		content.setName(PRODUCT_NAME);
		content.setImageLink(IMAGE_URL);
		content.setQuantity(BigDecimal.valueOf(TEST_QUANTITY));
		content.setPrice(TEST_PRICE.doubleValue());
		content.setStatus(ProductManagementStatusDTO.VISIBLE);
		content.createdAt(OFFSET_DATE_TIME);
		content.setTags(emptyList());
		return content;
	}

	public static ProductManagementPageDTO getProductManagementPageDTO() {
		ProductManagementPageDTO pageOrderDTO = new ProductManagementPageDTO();
		pageOrderDTO.setEmpty(false);
		pageOrderDTO.setTotalElements(100L);
		pageOrderDTO.setTotalPages(10);
		pageOrderDTO.setFirst(true);
		pageOrderDTO.setLast(false);
		pageOrderDTO.setNumber(1);
		pageOrderDTO.setNumberOfElements(10);
		pageOrderDTO.setSize(10);
		pageOrderDTO.content(singletonList(getProductManagementContentDTO()));
		return pageOrderDTO;
	}

	public static ProductRequestDTO getProductRequestDTO() {
		ProductRequestDTO productRequestDTO = new ProductRequestDTO();
		productRequestDTO.setStatus(ProductStatusDTO.VISIBLE);
		productRequestDTO.setImage(IMAGE_URL);
		productRequestDTO.setQuantity(TEST_QUANTITY);
		productRequestDTO.setPrice(TEST_PRICE);
		productRequestDTO.setTagIds(List.of(1L, 2L));
		productRequestDTO.setProductTranslations(List.of(getProductTranslationDTO()));

		return productRequestDTO;
	}

	public static ProductRequestDto getProductRequestDto() {
		return ProductRequestDto.builder().status(String.valueOf(ProductStatus.VISIBLE)).image(IMAGE_URL)
				.quantity(TEST_QUANTITY).price(TEST_PRICE).tagIds(List.of(1L, 2L))
				.productTranslations(Set.of(getProductTranslationDto())).build();
	}

	public static ProductTranslationDto getProductTranslationDto() {
		return ProductTranslationDto.builder().name("Name").description("Description").languageCode("en").build();
	}

	public static ProductTranslationDTO getProductTranslationDTO() {
		ProductTranslationDTO productTranslationDTO = new ProductTranslationDTO();
		productTranslationDTO.setLanguageCode("en");
		productTranslationDTO.setName("Name");
		productTranslationDTO.setDescription("Description");
		return productTranslationDTO;
	}

	public static ProductResponseDTO getProductResponseDTO() {
		ProductResponseDTO productResponseDTO = new ProductResponseDTO();
		productResponseDTO.setId(UUID.randomUUID());
		productResponseDTO.setStatus(ProductStatusDTO.VISIBLE);
		productResponseDTO.setImage("https://example.com/image.jpg");
		productResponseDTO.setCreatedAt(OffsetDateTime.now());
		productResponseDTO.setQuantity(10);
		productResponseDTO.setPrice(new BigDecimal("999.99"));

		List<TagDTO> tags = new ArrayList<>();
		tags.add(new TagDTO().id(BigDecimal.valueOf(1L)).name("Electronics"));
		tags.add(new TagDTO().id(BigDecimal.valueOf(2L)).name("Gadgets"));
		productResponseDTO.setTags(tags);

		List<ProductTranslationDTO> productTranslations = new ArrayList<>();
		productTranslations.add(new ProductTranslationDTO().languageCode("en").name("Sample Product")
				.description("Description in English"));
		productTranslations
				.add(new ProductTranslationDTO().languageCode("uk").name("Продукт").description("Опис Українською"));
		productResponseDTO.setProductTranslations(productTranslations);

		return productResponseDTO;
	}

	public static UpdateOrderStatusRequestDTO getUpdateOrderStatusRequestDTO() {
		var updateOrderStatusRequestDTO = new UpdateOrderStatusRequestDTO();
		updateOrderStatusRequestDTO.setOrderStatus(OrderStatusDTO.IN_PROGRESS);
		updateOrderStatusRequestDTO.setIsPaid(false);
		return updateOrderStatusRequestDTO;
	}

	public static UpdateOrderStatusDto getUpdateOrderStatusDto() {
		return UpdateOrderStatusDto.builder().status(OrderStatus.IN_PROGRESS).isPaid(false).build();
	}

	public static OrderStatusInfoDTO getOrderStatusInfoDTO() {
		var orderStatusInfoDTO = new OrderStatusInfoDTO();
		orderStatusInfoDTO.setAvailableStatuses(List.of(OrderStatusDTO.SHIPPED, OrderStatusDTO.DELIVERED,
				OrderStatusDTO.COMPLETED, OrderStatusDTO.CANCELED));
		orderStatusInfoDTO.setIsPaid(false);
		return orderStatusInfoDTO;
	}

	public static OrderStatusInfo getOrderStatusInfo() {
		return OrderStatusInfo.builder().availableStatuses(
				List.of(OrderStatus.SHIPPED, OrderStatus.DELIVERED, OrderStatus.COMPLETED, OrderStatus.CANCELED))
				.isPaid(false).build();
	}

	public static PageProductSearchResultDTO getPageProductSearchResultDTO() {
		PageProductSearchResultDTO pageProductSearchResultDTO = new PageProductSearchResultDTO();
		pageProductSearchResultDTO.setEmpty(false);
		pageProductSearchResultDTO.setTotalElements(100L);
		pageProductSearchResultDTO.setTotalPages(10);
		pageProductSearchResultDTO.setFirst(true);
		pageProductSearchResultDTO.setLast(false);
		pageProductSearchResultDTO.setNumber(1);
		pageProductSearchResultDTO.setNumberOfElements(10);
		pageProductSearchResultDTO.setSize(10);
		pageProductSearchResultDTO.content(singletonList(getProductSearchResultDTO()));
		return pageProductSearchResultDTO;
	}

	public static ProductSearchResultDTO getProductSearchResultDTO() {
		ProductSearchResultDTO productSearchResultDTO = new ProductSearchResultDTO();
		productSearchResultDTO.setId(TEST_UUID);
		productSearchResultDTO.setName(TAG_NAME);
		productSearchResultDTO.setImage("https://some/image");
		return productSearchResultDTO;
	}

	public static PageProductsDTO getPageProductsDTO() {
		var pageProductsDTO = new PageProductsDTO();
		pageProductsDTO.setContent(singletonList(getProductPreviewDTO()));
		pageProductsDTO.setTotalElements(1L);
		pageProductsDTO.totalPages(1);
		pageProductsDTO.first(true);
		pageProductsDTO.last(true);
		pageProductsDTO.number(1);
		pageProductsDTO.numberOfElements(1);
		pageProductsDTO.size(1);
		pageProductsDTO.empty(false);
		return pageProductsDTO;
	}

	public static ProductDetailsResponseDTO getProductDetailsResponseDTO() {
		var productDetailsResponseDTO = new ProductDetailsResponseDTO();
		productDetailsResponseDTO.name("Name");
		productDetailsResponseDTO.description("Desc");
		productDetailsResponseDTO.image(IMAGE_URL);
		productDetailsResponseDTO.tags(List.of("tag1", "tag2"));
		productDetailsResponseDTO.quantity(TEST_QUANTITY);
		productDetailsResponseDTO.price(TEST_PRICE);
		return productDetailsResponseDTO;
	}

	public static Account getAccount() {
		return new Account(TEST_ID, TEST_PASSWORD, TEST_EMAIL, TEST_FIRST_NAME, TEST_LAST_NAME, Role.ROLE_USER,
				UserStatus.ACTIVE, LocalDateTime.now());
	}

	public static Page<Account> getAccountPage() {
		return Page.<Account>builder().totalElements(1L).totalPages(1).first(true).last(true).number(0)
				.numberOfElements(1).size(5).empty(false).content(Collections.singletonList(getAccount())).build();
	}

	public static PageAccountsDTO getPageAccountsDTO() {
		var pageAccountsDTO = new PageAccountsDTO();
		pageAccountsDTO.setTotalElements(0L);
		pageAccountsDTO.setTotalPages(0);
		pageAccountsDTO.setFirst(true);
		pageAccountsDTO.setLast(true);
		pageAccountsDTO.setNumber(0);
		pageAccountsDTO.setNumberOfElements(0);
		pageAccountsDTO.setSize(5);
		pageAccountsDTO.setEmpty(true);
		pageAccountsDTO.setContent(Collections.emptyList());
		return pageAccountsDTO;
	}

	public static AccountManagementFilterDto getAccountManagementFilterDto() {
		return AccountManagementFilterDto.builder().status(UserStatus.ACTIVE).role(Role.ROLE_USER).build();
	}
}
