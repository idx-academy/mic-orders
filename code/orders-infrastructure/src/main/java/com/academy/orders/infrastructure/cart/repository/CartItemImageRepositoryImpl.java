package com.academy.orders.infrastructure.cart.repository;

import com.academy.orders.domain.cart.entity.CartItem;
import com.academy.orders.domain.cart.repository.CartItemImageRepository;
import com.academy.orders.domain.common.respository.ImageRepository;
import com.academy.orders.infrastructure.cart.CartItemMapper;
import com.academy.orders.infrastructure.product.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CartItemImageRepositoryImpl implements CartItemImageRepository {
	private final ImageRepository imageRepository;
	private final CartItemMapper cartItemMapper;
	private final ProductMapper productMapper;

	@Override
	public CartItem loadImageForProductInCart(CartItem cartItem) {
		var product = cartItem.product();
		var imageLink = imageRepository.getImageLinkByName(product.image());
		var productWithImage = productMapper.mapDomainImage(product, imageLink);
		return cartItemMapper.fromDomainWithProduct(cartItem, productWithImage);
	}
}
