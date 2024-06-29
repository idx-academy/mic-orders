package com.academy.orders.application.cart.usecase;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.cart.entity.CreateCartItemDTO;
import com.academy.orders.domain.cart.exception.CartItemAlreadyExistsException;
import com.academy.orders.domain.cart.repository.CartItemRepository;
import com.academy.orders.domain.product.entity.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.academy.orders.application.ModelUtils.getAccount;
import static com.academy.orders.application.ModelUtils.getProduct;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCartItemByUserUseCaseUseCaseTest {
	@Mock
	private CartItemRepository cartItemRepository;
	@InjectMocks
	private CreateCartItemByUserUseCaseUseCaseImpl createCartItemByUserUseCase;

	@Test
	void createCartItemByUserTest() {
		Account account = getAccount();
		Product product = getProduct();

		int quantity = 1;

		CreateCartItemDTO createCartItemDTO = CreateCartItemDTO.builder().productId(product.id()).userId(account.id())
				.quantity(quantity).build();
		CartItem cartItem = CartItem.builder().product(product).account(account).quantity(quantity).build();

		when(cartItemRepository.existsByProductIdAndUserId(createCartItemDTO.productId(), createCartItemDTO.userId()))
				.thenReturn(false);
		when(cartItemRepository.save(createCartItemDTO)).thenReturn(cartItem);

		createCartItemByUserUseCase.create(createCartItemDTO);

		verify(cartItemRepository).existsByProductIdAndUserId(createCartItemDTO.productId(),
				createCartItemDTO.userId());
		verify(cartItemRepository).save(createCartItemDTO);
	}

	@Test
	void createThrowsCartItemAlreadyExistsExceptionTest() {
		Account account = getAccount();
		Product product = getProduct();

		int quantity = 1;

		CreateCartItemDTO createCartItemDTO = CreateCartItemDTO.builder().productId(product.id()).userId(account.id())
				.quantity(quantity).build();

		when(cartItemRepository.existsByProductIdAndUserId(createCartItemDTO.productId(), createCartItemDTO.userId()))
				.thenReturn(true);

		assertThrows(CartItemAlreadyExistsException.class, () -> createCartItemByUserUseCase.create(createCartItemDTO));
	}
}
