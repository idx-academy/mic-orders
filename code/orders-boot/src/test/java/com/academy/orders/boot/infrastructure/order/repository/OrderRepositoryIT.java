package com.academy.orders.boot.infrastructure.order.repository;

import com.academy.orders.boot.infrastructure.common.repository.AbstractRepository;
import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.order.dto.OrdersFilterParametersDto;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.OrderItem;
import com.academy.orders.domain.order.entity.PostAddress;
import com.academy.orders.domain.order.entity.enumerated.DeliveryMethod;
import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders.domain.order.repository.OrderRepository;
import com.academy.orders.domain.product.entity.Language;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.entity.ProductTranslation;
import com.academy.orders.domain.product.entity.Tag;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.academy.orders.ModelUtils.getOrderWithoutId;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderRepositoryIT extends AbstractRepository {
	public static final UUID NOT_EXISTING_ORDER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
	private final Pageable pageable = new Pageable(0, 10, emptyList());
	private final String lang = "uk";
	private final Long accountId = 4L;
	private final UUID orderId = UUID.fromString("550e8400-e29b-41d4-a716-446655440015");
	@Autowired
	private OrderRepository orderRepository;

	@Test
	void findByIdTest() {
		var actual = orderRepository.findById(orderId);
		assertTrue(actual.isPresent());

		Order order = actual.get();
		validateBasicOrderNotNull(order);
		assertEquals(orderId, order.id());
	}

	@Test
	void findByIdReturnsEmptyOptionalIfOrderNotFoundTest() {
		var actual = orderRepository.findById(NOT_EXISTING_ORDER_ID);

		assertTrue(actual.isEmpty());
	}

	@Test
	void findByIdFetchOrderItemsDataTest() {
		var actual = orderRepository.findById(orderId, lang);
		assertTrue(actual.isPresent());

		Order order = actual.get();
		validateOrderNotNull(order);
		assertEquals(orderId, order.id());
	}

	@Test
	void findByIdFetchOrderItemsDataReturnsEmptyOptionalIfOrderNotFoundTest() {
		var actual = orderRepository.findById(NOT_EXISTING_ORDER_ID, lang);

		assertTrue(actual.isEmpty());
	}

	@Test
	void saveTest() {
		Order toSave = getOrderWithoutId();

		UUID savedOrderId = orderRepository.save(toSave, accountId);
		assertNotNull(savedOrderId);

		Optional<Order> optionalOrder = orderRepository.findById(savedOrderId, lang);

		assertTrue(optionalOrder.isPresent());
		Order order = optionalOrder.get();
		assertEquals(accountId, order.account().id());
		assertEquals(toSave.orderStatus(), order.orderStatus());
		assertEquals(toSave.isPaid(), order.isPaid());
		assertEquals(toSave.receiver(), order.receiver());
	}

	@Test
	void findAllByUserIdTest() {
		Page<Order> orderPage = orderRepository.findAllByUserId(accountId, lang, pageable);

		validatePageNotNull(orderPage);
		assertNotNull(orderPage.content());
		assertEquals(orderPage.content().size(), orderPage.numberOfElements());
		assertFalse(orderPage.content().isEmpty());
		orderPage.content().forEach(this::validateBasicOrderNotNull);
	}

	@Test
	void updateOrderStatusTest() {
		OrderStatus orderStatus = OrderStatus.SHIPPED;

		assertDoesNotThrow(() -> orderRepository.updateOrderStatus(orderId, orderStatus));

		Optional<Order> optionalOrder = orderRepository.findById(orderId);

		assertTrue(optionalOrder.isPresent());
		assertEquals(orderStatus, optionalOrder.get().orderStatus());
	}

	@Test
	void updateIsPaidStatusTest() {
		boolean isPaid = true;

		assertDoesNotThrow(() -> orderRepository.updateIsPaidStatus(orderId, isPaid));

		Optional<Order> optionalOrder = orderRepository.findById(orderId);

		assertTrue(optionalOrder.isPresent());
		assertEquals(isPaid, optionalOrder.get().isPaid());
	}

	@Test
	void findAllWithEmptyFilterTest() {
		OrdersFilterParametersDto filterParametersDto = OrdersFilterParametersDto.builder().deliveryMethods(emptyList())
				.statuses(emptyList()).build();
		Page<Order> orderPage = orderRepository.findAll(filterParametersDto, pageable);

		validatePageNotNull(orderPage);
		assertNotNull(orderPage.content());
		assertEquals(orderPage.content().size(), orderPage.numberOfElements());
		assertFalse(orderPage.content().isEmpty());
		orderPage.content().forEach(this::validateBasicOrderNotNull);
	}

	@Test
	void findAllWithBoundedByDateTest() {
		LocalDateTime createdAfter = LocalDateTime.of(2021, 1, 1, 1, 1, 1);
		LocalDateTime createdBefore = LocalDateTime.of(2027, 1, 1, 1, 1, 1);
		OrdersFilterParametersDto filterParametersDto = OrdersFilterParametersDto.builder().createdAfter(createdAfter)
				.deliveryMethods(emptyList()).statuses(emptyList()).createdBefore(createdBefore).build();
		Page<Order> orderPage = orderRepository.findAll(filterParametersDto, pageable);

		validatePageNotNull(orderPage);
		assertNotNull(orderPage.content());
		assertEquals(orderPage.content().size(), orderPage.numberOfElements());
		assertFalse(orderPage.content().isEmpty());
		orderPage.content().forEach(this::validateBasicOrderNotNull);
		orderPage.content().forEach(order -> assertTrue(
				order.createdAt().isAfter(createdAfter) && order.createdAt().isBefore(createdBefore)));
	}

	@Test
	void findAllWithBoundedByTotalPriceTest() {
		BigDecimal totalMore = BigDecimal.valueOf(30);
		BigDecimal totalLess = BigDecimal.valueOf(300);
		OrdersFilterParametersDto filterParametersDto = OrdersFilterParametersDto.builder().totalMore(totalMore)
				.deliveryMethods(emptyList()).statuses(emptyList()).totalLess(totalLess).build();
		Page<Order> orderPage = orderRepository.findAll(filterParametersDto, pageable);

		validatePageNotNull(orderPage);
		assertNotNull(orderPage.content());
		assertEquals(orderPage.content().size(), orderPage.numberOfElements());
		assertFalse(orderPage.content().isEmpty());
		orderPage.content().forEach(this::validateBasicOrderNotNull);
	}

	@Test
	void findAllWithIsPaidTrueTest() {
		OrdersFilterParametersDto filterParametersDto = OrdersFilterParametersDto.builder().deliveryMethods(emptyList())
				.statuses(emptyList()).isPaid(true).build();
		Page<Order> orderPage = orderRepository.findAll(filterParametersDto, pageable);

		validatePageNotNull(orderPage);
		assertNotNull(orderPage.content());
		assertEquals(orderPage.content().size(), orderPage.numberOfElements());
		assertFalse(orderPage.content().isEmpty());
		orderPage.content().forEach(this::validateBasicOrderNotNull);
		orderPage.content().forEach(order -> assertTrue(order.isPaid()));
	}

	@Test
	void findAllWithStatusesTest() {
		List<OrderStatus> statuses = List.of(OrderStatus.IN_PROGRESS, OrderStatus.COMPLETED);
		OrdersFilterParametersDto filterParametersDto = OrdersFilterParametersDto.builder().deliveryMethods(emptyList())
				.statuses(statuses).build();
		Page<Order> orderPage = orderRepository.findAll(filterParametersDto, pageable);

		validatePageNotNull(orderPage);
		assertNotNull(orderPage.content());
		assertEquals(orderPage.content().size(), orderPage.numberOfElements());
		assertFalse(orderPage.content().isEmpty());
		orderPage.content().forEach(this::validateBasicOrderNotNull);
		orderPage.content().forEach(order -> assertTrue(statuses.contains(order.orderStatus())));
	}

	@Test
	void findAllWithDeliveryMethodsTest() {
		List<DeliveryMethod> deliveryMethods = List.of(DeliveryMethod.NOVA, DeliveryMethod.UKRPOSHTA);
		OrdersFilterParametersDto filterParametersDto = OrdersFilterParametersDto.builder().statuses(emptyList())
				.deliveryMethods(deliveryMethods).build();
		Page<Order> orderPage = orderRepository.findAll(filterParametersDto, pageable);

		validatePageNotNull(orderPage);
		assertNotNull(orderPage.content());
		assertEquals(orderPage.content().size(), orderPage.numberOfElements());
		assertFalse(orderPage.content().isEmpty());
		orderPage.content().forEach(this::validateBasicOrderNotNull);
		orderPage.content()
				.forEach(order -> assertTrue(deliveryMethods.contains(order.postAddress().deliveryMethod())));
	}

	@Test
	void findByIdFetchDataTest() {
		var actual = orderRepository.findByIdFetchData(orderId);
		assertTrue(actual.isPresent());

		Order order = actual.get();
		validateBasicOrderNotNull(order);
		assertEquals(orderId, order.id());
	}

	@Test
	void findByIdFetchDataWhenOrderNotFoundTest() {
		var actual = orderRepository.findByIdFetchData(NOT_EXISTING_ORDER_ID);

		assertTrue(actual.isEmpty());
	}

	private void validatePageNotNull(Page<Order> orderPage) {
		assertNotNull(orderPage);
		assertNotNull(orderPage.totalElements());
		assertNotNull(orderPage.totalPages());
		assertNotNull(orderPage.first());
		assertNotNull(orderPage.last());
		assertNotNull(orderPage.number());
		assertNotNull(orderPage.numberOfElements());
		assertNotNull(orderPage.numberOfElements());
		assertNotNull(orderPage.empty());
	}

	private void validateOrderNotNull(Order order) {
		validateBasicOrderNotNull(order);
		validateOrderItemsNotNull(order.orderItems());
		validateAccountNotNull(order.account());
		validatePostAddressNotNull(order.postAddress());
	}

	private void validateOrderItemsNotNull(List<OrderItem> orderItems) {
		assertNotNull(orderItems);
		assertFalse(orderItems.isEmpty());
		orderItems.forEach(this::validateOrderItem);
	}

	private void validateOrderItem(OrderItem orderItem) {
		assertNotNull(orderItem);
		validateProductNotNull(orderItem.product());
		assertNotNull(orderItem.quantity());
		assertNotNull(orderItem.price());
	}

	private void validateProductNotNull(Product product) {
		assertNotNull(product);
		assertNotNull(product.id());
		assertNotNull(product.status());
		assertNotNull(product.image());
		assertNotNull(product.createdAt());
		assertNotNull(product.quantity());
		assertNotNull(product.price());
		validateTagsNotNull(product.tags());
		validateProductTranslationsNotNull(product.productTranslations());
	}

	private void validateProductTranslationsNotNull(Set<ProductTranslation> productTranslations) {
		assertNotNull(productTranslations);
		assertFalse(productTranslations.isEmpty());
		productTranslations.forEach(this::validateProductTranslationNotNull);
	}

	private void validateProductTranslationNotNull(ProductTranslation productTranslation) {
		assertNotNull(productTranslation);
		assertNotNull(productTranslation.description());
		assertNotNull(productTranslation.name());
		validateLanguageNotNull(productTranslation.language());
	}

	private void validateLanguageNotNull(Language language) {
		assertNotNull(language);
		assertNotNull(language.id());
		assertNotNull(language.code());
	}

	private void validateTagsNotNull(Set<Tag> tags) {
		assertNotNull(tags);
		assertFalse(tags.isEmpty());
		tags.forEach(this::validateTagsNotNull);
	}

	private void validateTagsNotNull(Tag tag) {
		assertNotNull(tag);
		assertNotNull(tag.id());
		assertNotNull(tag.name());
	}

	private void validatePostAddressNotNull(PostAddress postAddress) {
		assertNotNull(postAddress);
		assertNotNull(postAddress.deliveryMethod());
		assertNotNull(postAddress.city());
		assertNotNull(postAddress.department());
	}

	private void validateAccountNotNull(Account account) {
		assertNotNull(account);
		assertNotNull(account.id());
		assertNotNull(account.password());
		assertNotNull(account.email());
		assertNotNull(account.firstName());
		assertNotNull(account.lastName());
		assertNotNull(account.role());
		assertNotNull(account.status());
		assertNotNull(account.createdAt());
	}

	private void validateBasicOrderNotNull(Order order) {
		assertNotNull(order);
		assertNotNull(order.id());
		assertNotNull(order.orderStatus());
		assertNotNull(order.createdAt());
		assertNotNull(order.editedAt());
		assertNotNull(order.isPaid());
		validateReceiverNotNull(order);
	}

	private void validateReceiverNotNull(Order order) {
		assertNotNull(order.receiver());
		assertNotNull(order.receiver().firstName());
		assertNotNull(order.receiver().lastName());
		assertNotNull(order.receiver().email());
	}
}
