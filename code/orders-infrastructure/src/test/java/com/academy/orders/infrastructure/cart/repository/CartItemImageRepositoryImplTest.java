package com.academy.orders.infrastructure.cart.repository;

import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.common.respository.ImageRepository;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.infrastructure.ModelUtils;
import com.academy.orders.infrastructure.cart.CartItemMapper;
import com.academy.orders.infrastructure.product.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.academy.orders.infrastructure.ModelUtils.TEST_IMAGE_LINK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartItemImageRepositoryImplTest {
    @InjectMocks
    private CartItemImageRepositoryImpl cartItemImageRepository;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private CartItemMapper cartItemMapper;
    @Mock
    private ProductMapper productMapper;

    @Test
    void loadImageForProductInCart(){
        var cartItem = ModelUtils.getCartItem();
        var product = cartItem.product();

        var productWithLink = Product.builder().image(TEST_IMAGE_LINK).build();
        var cartItemWithProduct = CartItem.builder().product(productWithLink).build();

        when(imageRepository.getImageLinkByName(product.image())).thenReturn(TEST_IMAGE_LINK);
        when(productMapper.mapDomainImage(product, TEST_IMAGE_LINK)).thenReturn(productWithLink);
        when(cartItemMapper.fromDomainWithProduct(cartItem, productWithLink)).thenReturn(cartItemWithProduct);

        var actualCartItem = cartItemImageRepository.loadImageForProductInCart(cartItem);

        assertEquals(cartItemWithProduct, actualCartItem);

        verify(imageRepository).getImageLinkByName(product.image());
        verify(productMapper).mapDomainImage(product,TEST_IMAGE_LINK);
        verify(cartItemMapper).fromDomainWithProduct(cartItem, productWithLink);
    }
}
