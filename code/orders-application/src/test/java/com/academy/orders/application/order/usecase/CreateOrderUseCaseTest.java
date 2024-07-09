package com.academy.orders.application.order.usecase;

import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.cart.exception.EmptyCartException;
import com.academy.orders.domain.cart.repository.CartItemRepository;
import com.academy.orders.domain.order.dto.CreateOrderDto;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.OrderItem;
import com.academy.orders.domain.order.entity.OrderReceiver;
import com.academy.orders.domain.order.entity.PostAddress;
import com.academy.orders.domain.order.entity.enumerated.OrderStatus;
import com.academy.orders.domain.order.exception.InsufficientProductQuantityException;
import com.academy.orders.domain.order.repository.OrderRepository;
import com.academy.orders.domain.product.usecase.CalculatePriceUseCase;
import com.academy.orders.domain.order.usecase.ChangeQuantityUseCase;
import com.academy.orders.domain.product.entity.Product;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.academy.orders.application.ModelUtils.getCartItem;
import static com.academy.orders.application.ModelUtils.getCreateOrderDto;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateOrderUseCaseTest {
	@InjectMocks
	private CreateOrderUseCaseImpl createOrderUseCase;
	@Mock
	private CalculatePriceUseCase calculatePriceUseCase;
	@Mock
	private ChangeQuantityUseCase changeQuantityUseCase;
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private CartItemRepository cartItemRepository;

	private CreateOrderDto createOrderDto;
	private CartItem cartItem;
	private BigDecimal calculatedPrice;

	@BeforeEach
	void setUp() {
		createOrderDto = getCreateOrderDto();
		cartItem = getCartItem();
		calculatedPrice = cartItem.product().price().multiply(BigDecimal.valueOf(cartItem.quantity()));

	}

	@Test
	void testCreateOrder() {
		var expectedOrderId = UUID.randomUUID();
		var order = Order.builder().receiver(getOrderReceiver()).postAddress(getPostAddress())
				.orderStatus(OrderStatus.IN_PROGRESS)
				.orderItems(singletonList(new OrderItem(cartItem.product(), calculatedPrice, cartItem.quantity())))
				.isPaid(false).build();

		when(cartItemRepository.findCartItemsByAccountId(anyLong())).thenReturn(singletonList(cartItem));
		when(calculatePriceUseCase.calculateTotalPrice(any(Product.class), anyInt())).thenReturn(calculatedPrice);
		doNothing().when(changeQuantityUseCase).changeQuantityOfProduct(any(Product.class), anyInt());
		when(orderRepository.save(eq(order), anyLong())).thenReturn(expectedOrderId);
		doNothing().when(cartItemRepository).deleteCartItemsByAccountId(anyLong());

		var actualOrderId = createOrderUseCase.createOrder(createOrderDto, 1L);

		assertEquals(expectedOrderId, actualOrderId);

		verify(cartItemRepository).findCartItemsByAccountId(anyLong());
		verify(calculatePriceUseCase).calculateTotalPrice(any(Product.class), anyInt());
		verify(changeQuantityUseCase).changeQuantityOfProduct(any(Product.class), anyInt());
		verify(orderRepository).save(any(Order.class), anyLong());
		verify(cartItemRepository).deleteCartItemsByAccountId(anyLong());
	}

	private OrderReceiver getOrderReceiver() {
		return OrderReceiver.builder().firstName(createOrderDto.firstName()).lastName(createOrderDto.lastName())
				.email(createOrderDto.email()).build();
	}

	private PostAddress getPostAddress() {
		return PostAddress.builder().city(createOrderDto.city()).department(createOrderDto.department())
				.deliveryMethod(createOrderDto.deliveryMethod()).build();
	}

	@Test
	void testCreateOrderThrowsInsufficientProductQuantityException() {
		when(cartItemRepository.findCartItemsByAccountId(anyLong())).thenReturn(singletonList(cartItem));
		when(calculatePriceUseCase.calculateTotalPrice(any(Product.class), anyInt())).thenReturn(calculatedPrice);
		doThrow(InsufficientProductQuantityException.class).when(changeQuantityUseCase)
				.changeQuantityOfProduct(any(Product.class), anyInt());

		assertThrows(InsufficientProductQuantityException.class,
				() -> createOrderUseCase.createOrder(createOrderDto, 1L));

		verify(cartItemRepository).findCartItemsByAccountId(anyLong());
		verify(calculatePriceUseCase).calculateTotalPrice(any(Product.class), anyInt());
		verify(changeQuantityUseCase).changeQuantityOfProduct(any(Product.class), anyInt());
	}

	@Test
	void testCreateOrderThrowsEmptyCartException() {
		when(cartItemRepository.findCartItemsByAccountId(anyLong())).thenReturn(emptyList());

		assertThrows(EmptyCartException.class, () -> createOrderUseCase.createOrder(createOrderDto, 1L));
		verify(cartItemRepository).findCartItemsByAccountId(anyLong());
	}
}