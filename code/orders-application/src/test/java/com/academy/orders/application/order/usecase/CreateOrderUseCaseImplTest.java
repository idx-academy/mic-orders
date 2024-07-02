package com.academy.orders.application.order.usecase;

import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.cart.exception.EmptyCartException;
import com.academy.orders.domain.cart.repository.CartItemRepository;
import com.academy.orders.domain.order.dto.CreateOrderDto;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.exception.InsufficientProductQuantityException;
import com.academy.orders.domain.order.repository.OrderRepository;
import com.academy.orders.domain.order.usecase.CalculatePriceUseCase;
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
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateOrderUseCaseImplTest {
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

		when(cartItemRepository.findCartItemsByAccountId(anyLong())).thenReturn(singletonList(cartItem));
		when(calculatePriceUseCase.calculatePriceForOrder(any(Product.class), anyInt())).thenReturn(calculatedPrice);
		doNothing().when(changeQuantityUseCase).changeQuantityOfProduct(any(Product.class), anyInt());
		when(orderRepository.save(any(Order.class), anyLong())).thenReturn(expectedOrderId);
		doNothing().when(cartItemRepository).deleteUsersCartItems(anyLong());

		var actualOrderId = createOrderUseCase.createOrder(createOrderDto, 1L);

		assertEquals(expectedOrderId, actualOrderId);

		verify(cartItemRepository).findCartItemsByAccountId(anyLong());
		verify(calculatePriceUseCase).calculatePriceForOrder(any(Product.class), anyInt());
		verify(changeQuantityUseCase).changeQuantityOfProduct(any(Product.class), anyInt());
		verify(orderRepository).save(any(Order.class), anyLong());
		verify(cartItemRepository).deleteUsersCartItems(anyLong());

	}

	@Test
	void testCreateOrderThrowsInsufficientProductQuantityException() {
		when(cartItemRepository.findCartItemsByAccountId(anyLong())).thenReturn(singletonList(cartItem));
		when(calculatePriceUseCase.calculatePriceForOrder(any(Product.class), anyInt())).thenReturn(calculatedPrice);
		doThrow(InsufficientProductQuantityException.class).when(changeQuantityUseCase)
				.changeQuantityOfProduct(any(Product.class), anyInt());

		assertThrows(InsufficientProductQuantityException.class,
				() -> createOrderUseCase.createOrder(createOrderDto, 1L));

		verify(cartItemRepository).findCartItemsByAccountId(anyLong());
		verify(calculatePriceUseCase).calculatePriceForOrder(any(Product.class), anyInt());
		verify(changeQuantityUseCase).changeQuantityOfProduct(any(Product.class), anyInt());
	}

	@Test
	void testCreateOrderThrowsEmptyCartException() {
		when(cartItemRepository.findCartItemsByAccountId(anyLong())).thenReturn(emptyList());

		assertThrows(EmptyCartException.class, () -> createOrderUseCase.createOrder(createOrderDto, 1L));
		verify(cartItemRepository).findCartItemsByAccountId(anyLong());
	}
}
