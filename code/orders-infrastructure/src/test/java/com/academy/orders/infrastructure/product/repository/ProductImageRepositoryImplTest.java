package com.academy.orders.infrastructure.product.repository;

import com.academy.orders.domain.common.respository.ImageRepository;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.infrastructure.ModelUtils;
import com.academy.orders.infrastructure.product.ProductMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductImageRepositoryImplTest {
	@InjectMocks
	private ProductImageRepositoryImpl productImageRepository;
	@Mock
	private ImageRepository imageRepository;
	@Mock
	private ProductMapper productMapper;

	@Test
	void loadImageForProductTest() {
		var product = ModelUtils.getProduct();
		var imageLink = ModelUtils.TEST_IMAGE_LINK;
		var productWithLink = Product.builder().image(imageLink).build();

		when(imageRepository.getImageLinkByName(product.image())).thenReturn(imageLink);
		when(productMapper.mapDomainImage(product, imageLink)).thenReturn(productWithLink);
		var result = productImageRepository.loadImageForProduct(product);

		assertEquals(productWithLink, result);
		verify(imageRepository).getImageLinkByName(product.image());
		verify(productMapper).mapDomainImage(product, imageLink);
	}

}
