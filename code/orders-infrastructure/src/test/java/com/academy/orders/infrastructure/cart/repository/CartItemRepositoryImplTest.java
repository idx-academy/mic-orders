package com.academy.orders.infrastructure.cart.repository;

import com.academy.orders.domain.account.exception.AccountNotFoundException;
import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.cart.entity.CreateCartItemDTO;
import com.academy.orders.domain.product.exception.ProductNotFoundException;
import com.academy.orders.infrastructure.account.entity.AccountEntity;
import com.academy.orders.infrastructure.account.repository.AccountJpaAdapter;
import com.academy.orders.infrastructure.cart.CartItemMapper;
import com.academy.orders.infrastructure.cart.entity.CartItemEntity;
import com.academy.orders.infrastructure.cart.entity.CartItemId;
import com.academy.orders.infrastructure.product.entity.ProductEntity;
import com.academy.orders.infrastructure.product.repository.ProductJpaAdapter;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.academy.orders.infrastructure.ModelUtils.getAccount;
import static com.academy.orders.infrastructure.ModelUtils.getProduct;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartItemRepositoryImplTest {
	@InjectMocks
	private CartItemRepositoryImpl cartItemRepository;
	@Mock
	private CartItemJpaAdapter cartItemJpaAdapter;
	@Mock
	private CartItemMapper cartItemMapper;
	@Mock
	private AccountJpaAdapter accountJpaAdapter;
	@Mock
	private ProductJpaAdapter productJpaAdapter;

	private CreateCartItemDTO createCartItemDto;
	private CartItemEntity cartItemEntity;

	@BeforeEach
	void setUp() {
		createCartItemDto = new CreateCartItemDTO(UUID.randomUUID(), 1L, 1);
		cartItemEntity = CartItemEntity.builder().quantity(1).build();
	}

	@ParameterizedTest
	@CsvSource({"true", "false"})
	void testExistsByProductAndUserId(Boolean response) {

		when(cartItemJpaAdapter.existsById(any(CartItemId.class))).thenReturn(response);

		assertEquals(response,cartItemRepository.existsByProductIdAndUserId(UUID.randomUUID(), 1L));
		verify(cartItemJpaAdapter).existsById(any(CartItemId.class));
	}

	@Test
	void testSaveCartItem() {
		var account = getAccount();
		var productEntity = getProduct();
		var expected = CartItem.builder().product(productEntity).account(account).quantity(1).build();

		when(cartItemMapper.toEntity(createCartItemDto)).thenReturn(cartItemEntity);
		when(productJpaAdapter.findById(createCartItemDto.productId())).thenReturn(Optional.of(new ProductEntity()));
		when(accountJpaAdapter.findById(createCartItemDto.userId())).thenReturn(Optional.of(new AccountEntity()));
		when(cartItemJpaAdapter.save(any(CartItemEntity.class))).thenReturn(cartItemEntity);
		when(cartItemMapper.fromEntity(cartItemEntity)).thenReturn(expected);

		var actualOrderItem = cartItemRepository.save(createCartItemDto);

		assertEquals(expected, actualOrderItem);
		assertNotNull(cartItemEntity.getAccount());
		assertNotNull(cartItemEntity.getProduct());

		verify(cartItemMapper).toEntity(any(CreateCartItemDTO.class));
		verify(productJpaAdapter).findById(any(UUID.class));
		verify(accountJpaAdapter).findById(anyLong());
		verify(cartItemJpaAdapter).save(any(CartItemEntity.class));
		verify(cartItemMapper).fromEntity(any(CartItemEntity.class));
	}

	@Test
	void testIncrementQuantity() {
		doNothing().when(cartItemJpaAdapter).increaseQuantity(any(CartItemId.class), anyInt());

		assertDoesNotThrow(() -> cartItemRepository.incrementQuantity(UUID.randomUUID(), 1L));
		verify(cartItemJpaAdapter).increaseQuantity(any(CartItemId.class), anyInt());
	}

}
