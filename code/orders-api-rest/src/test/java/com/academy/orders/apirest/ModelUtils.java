package com.academy.orders.apirest;

import com.academy.orders.domain.cart.dto.UpdatedCartItemDto;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.order.dto.OrderStatusInfo;
import com.academy.orders.domain.order.dto.OrdersFilterParametersDto;
import com.academy.orders.domain.order.dto.UpdateOrderStatusDto;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.OrderItem;
import com.academy.orders.domain.order.entity.OrderReceiver;
import com.academy.orders.domain.order.entity.PostAddress;
import com.academy.orders.domain.order.entity.enumerated.DeliveryMethod;
import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders.domain.product.dto.ProductManagementFilterDto;
import com.academy.orders.domain.product.dto.UpdateProductDto;
import com.academy.orders.domain.product.entity.Language;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.ProductTranslation;
import com.academy.orders.domain.product.entity.Tag;
import com.academy.orders.domain.product.entity.enumerated.ProductStatus;
import com.academy.orders_api_rest.generated.model.PageProductSearchResultDTO;
import com.academy.orders_api_rest.generated.model.ProductSearchResultDTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import com.academy.orders_api_rest.generated.model.AccountResponseDTO;
import com.academy.orders_api_rest.generated.model.CartItemDTO;
import com.academy.orders_api_rest.generated.model.CartItemsResponseDTO;
import com.academy.orders_api_rest.generated.model.CreateProductRequestDTO;
import com.academy.orders_api_rest.generated.model.ManagerOrderDTO;
import com.academy.orders_api_rest.generated.model.OrderItemDTO;
import com.academy.orders_api_rest.generated.model.OrderReceiverDTO;
import com.academy.orders_api_rest.generated.model.OrderStatusDTO;
import com.academy.orders_api_rest.generated.model.OrderStatusInfoDTO;
import com.academy.orders_api_rest.generated.model.OrdersFilterParametersDTO;
import com.academy.orders_api_rest.generated.model.PageManagerOrderPreviewDTO;
import com.academy.orders_api_rest.generated.model.PageUserOrderDTO;
import com.academy.orders_api_rest.generated.model.PageableDTO;
import com.academy.orders_api_rest.generated.model.PlaceOrderRequestDTO;
import com.academy.orders_api_rest.generated.model.PostAddressDTO;
import com.academy.orders_api_rest.generated.model.ProductManagementContentDTO;
import com.academy.orders_api_rest.generated.model.ProductManagementPageDTO;
import com.academy.orders_api_rest.generated.model.ProductManagementStatusDTO;
import com.academy.orders_api_rest.generated.model.ProductPreviewDTO;
import com.academy.orders_api_rest.generated.model.ProductResponseDTO;
import com.academy.orders_api_rest.generated.model.ProductStatusDTO;
import com.academy.orders_api_rest.generated.model.ProductTranslationDTO;
import com.academy.orders_api_rest.generated.model.TagDTO;
import com.academy.orders_api_rest.generated.model.UpdateOrderStatusRequestDTO;
import com.academy.orders_api_rest.generated.model.UpdateProductRequestDTO;
import com.academy.orders_api_rest.generated.model.UpdatedCartItemDTO;
import com.academy.orders_api_rest.generated.model.UserOrderDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
		return ordersFilterParametersDTO;
	}

	public static OrdersFilterParametersDto getOrdersFilterParametersDto() {
		return OrdersFilterParametersDto.builder().deliveryMethods(List.of(DeliveryMethod.NOVA))
				.statuses(List.of(OrderStatus.IN_PROGRESS)).isPaid(false).createdBefore(DATE_TIME)
				.createdAfter(DATE_TIME).totalMore(BigDecimal.ZERO).totalLess(BigDecimal.TEN).build();
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

	public static UpdateProductRequestDTO getUpdateProductRequestDTO() {
		UpdateProductRequestDTO updateProductRequestDTO = new UpdateProductRequestDTO();
		updateProductRequestDTO.setName("Name");
		updateProductRequestDTO.setDescription("Description");
		updateProductRequestDTO.setStatus(ProductStatusDTO.VISIBLE);
		updateProductRequestDTO.setImage("image");
		updateProductRequestDTO.setQuantity(10);
		updateProductRequestDTO.setPrice(BigDecimal.valueOf(100));
		updateProductRequestDTO.setTagIds(List.of(TEST_ID));
		return updateProductRequestDTO;
	}

	public static UpdateProductDto getUpdateProduct() {
		return UpdateProductDto.builder().id(TEST_UUID).name("Name").description("Description")
				.status(String.valueOf(ProductStatusDTO.VISIBLE)).image(IMAGE_URL).quantity(10)
				.price(BigDecimal.valueOf(100)).tagIds(List.of(TEST_ID)).createdAt(LocalDateTime.now()).build();
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

	public static CreateProductRequestDTO getCreateProductRequestDTO() {
		CreateProductRequestDTO createProductRequestDTO = new CreateProductRequestDTO();
		createProductRequestDTO.setStatus(ProductStatusDTO.VISIBLE);
		createProductRequestDTO.setImage("https://example.com/image.jpg");
		createProductRequestDTO.setQuantity(10);
		createProductRequestDTO.setPrice(new BigDecimal("999.99"));

		List<Long> tagIds = new ArrayList<>();
		tagIds.add(1L);
		tagIds.add(2L);
		createProductRequestDTO.setTagIds(tagIds);

		List<ProductTranslationDTO> productTranslations = new ArrayList<>();
		productTranslations.add(new ProductTranslationDTO().languageCode("en").name("Sample Product")
				.description("Description in English"));
		productTranslations
				.add(new ProductTranslationDTO().languageCode("uk").name("Продукт").description("Опис Українською"));
		createProductRequestDTO.setProductTranslations(productTranslations);

		return createProductRequestDTO;
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
		orderStatusInfoDTO.setAvailableStatuses(List.of("SHIPPED, DELIVERED, COMPLETED, CANCELED"));
		orderStatusInfoDTO.setIsPaid(false);
		return orderStatusInfoDTO;
	}

	public static OrderStatusInfo getOrderStatusInfo() {
		return OrderStatusInfo.builder().availableStatuses(List.of("SHIPPED, DELIVERED, COMPLETED, CANCELED"))
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
}
