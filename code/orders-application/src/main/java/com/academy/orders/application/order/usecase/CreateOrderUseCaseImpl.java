package com.academy.orders.application.order.usecase;

import com.academy.orders.domain.order.dto.CreateOrderDto;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.OrderItem;
import com.academy.orders.domain.order.entity.OrderReceiver;
import com.academy.orders.domain.order.entity.PostAddress;
import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders.domain.order.repository.OrderRepository;
import com.academy.orders.domain.order.usecase.CalculatePriceUseCase;
import com.academy.orders.domain.order.usecase.ChangeQuantityUseCase;
import com.academy.orders.domain.order.usecase.CreateOrderUseCase;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.repository.ProductRepository;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCaseImpl implements CreateOrderUseCase {
	private final CalculatePriceUseCase calculatePriceUseCase;
	private final ChangeQuantityUseCase changeQuantityUseCase;
	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;

	@Override
	public UUID createOrder(CreateOrderDto createOrderDto, String userEmail) {
		var order = createOrderObject(createOrderDto);
		var bucketElements = getBucketElements();
		var orderItems = createOrderItems(bucketElements);
		var orderWithItems = order.addOrderItems(orderItems);
		return saveOrder(orderWithItems, userEmail);
	}

	private Order createOrderObject(CreateOrderDto createOrderDto) {
		return Order.builder().receiver(createReceiverObject(createOrderDto))
				.postAddress(createPostAddressObject(createOrderDto)).orderStatus(OrderStatus.IN_PROGRESS).isPaid(false)
				.build();
	}

	private PostAddress createPostAddressObject(CreateOrderDto createOrderDto) {
		return PostAddress.builder().city(createOrderDto.city()).department(createOrderDto.department())
				.deliveryMethod(createOrderDto.deliveryMethod()).build();
	}

	private OrderReceiver createReceiverObject(CreateOrderDto createOrderDto) {
		return OrderReceiver.builder().firstName(createOrderDto.firstName()).lastName(createOrderDto.lastName())
				.email(createOrderDto.email()).build();
	}

	private Map<Product, Integer> getBucketElements() {
		var products = productRepository.findProductsWithPricesAndQuantities(
				UUID.fromString("84b7e490-0dcf-44c3-beb6-7496dc6ef3b0"),
				UUID.fromString("04f7a6b4-e55e-4f68-9f68-9636e1b2e256"));
		return products.stream().collect(Collectors.toMap(f -> f, f -> 3));
	}

	private List<OrderItem> createOrderItems(Map<Product, Integer> bucketElements) {
		return bucketElements.entrySet().stream().map(this::createItem).toList();
	}

	private OrderItem createItem(Map.Entry<Product, Integer> element) {
		var calculatedPrice = calculatePriceUseCase.calculatePriceForOrder(element.getKey(), element.getValue());
		changeQuantityUseCase.changeQuantityOfProduct(element.getKey(), element.getValue());
		return new OrderItem(element.getKey(), calculatedPrice, element.getValue());
	}

	private UUID saveOrder(Order order, String userEmail) {
		return orderRepository.save(order, userEmail);
	}
}
