package com.academy.orders.application.cart.usecase;

import com.academy.orders.domain.cart.exception.CartItemNotFoundException;
import com.academy.orders.domain.cart.repository.CartItemRepository;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteProductFromCartUseCaseImplTest {
    @InjectMocks
    private DeleteProductFromCartUseCaseImpl deleteProductFromCartUseCase;
    @Mock
    private CartItemRepository cartItemRepository;

    @Test
    void deleteProductFromCart() {
        var accountId = 1L;
        var productId = UUID.randomUUID();

        when(cartItemRepository.existsByProductIdAndUserId(productId, accountId)).thenReturn(true);
        doNothing().when(cartItemRepository).deleteCartItemByAccountAndProductIds(accountId,productId);

        assertDoesNotThrow(()-> deleteProductFromCartUseCase.deleteProductFromCart(accountId,productId));
        verify(cartItemRepository).existsByProductIdAndUserId(any(UUID.class),anyLong());
        verify(cartItemRepository).deleteCartItemByAccountAndProductIds(anyLong(),any(UUID.class));
    }

    @Test
    void deleteProductFromCartThrowsCartItemNotFoundException() {
        var accountId = 1L;
        var productId = UUID.randomUUID();

        when(cartItemRepository.existsByProductIdAndUserId(productId, accountId)).thenReturn(false);

        assertThrows(CartItemNotFoundException.class,()-> deleteProductFromCartUseCase.deleteProductFromCart(accountId,productId));
        verify(cartItemRepository).existsByProductIdAndUserId(any(UUID.class),anyLong());
        verify(cartItemRepository, never()).deleteCartItemByAccountAndProductIds(anyLong(),any(UUID.class));
    }
}
