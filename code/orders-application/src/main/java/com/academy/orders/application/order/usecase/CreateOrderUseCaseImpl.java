package com.academy.orders.application.order.usecase;

import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.cart.repository.CartItemRepository;
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
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCaseImpl implements CreateOrderUseCase {
	private final CalculatePriceUseCase calculatePriceUseCase;
	private final ChangeQuantityUseCase changeQuantityUseCase;
	private final OrderRepository orderRepository;
	private final CartItemRepository cartItemRepository;

	@Override
	public UUID createOrder(CreateOrderDto createOrderDto, Long accountId) {
		var order = createOrderObject(createOrderDto);
		var bucketElements = getBucketElements(accountId);
		var orderItems = createOrderItems(bucketElements);
		var orderWithItems = order.addOrderItems(orderItems);
		return saveOrder(orderWithItems, accountId);
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

	private List<CartItem> getBucketElements(Long accountId) {
		return cartItemRepository.findCartItemsByAccountId(accountId);
	}

	private List<OrderItem> createOrderItems(List<CartItem> cartItems) {
		return cartItems.stream().map(this::createItem).toList();
	}

	private OrderItem createItem(CartItem cartItem) {
		var calculatedPrice = calculatePriceUseCase.calculatePriceForOrder(cartItem.product(), cartItem.quantity());
		changeQuantityUseCase.changeQuantityOfProduct(cartItem.product(), cartItem.quantity());
		return new OrderItem(cartItem.product(), calculatedPrice, cartItem.quantity());
	}

	private UUID saveOrder(Order order, Long accountId) {
		return orderRepository.save(order, accountId);
	}
}
