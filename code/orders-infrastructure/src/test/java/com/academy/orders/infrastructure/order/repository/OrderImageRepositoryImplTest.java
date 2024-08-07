package com.academy.orders.infrastructure.order.repository;

import com.academy.orders.domain.common.respository.ImageRepository;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.OrderItem;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.infrastructure.ModelUtils;
import com.academy.orders.infrastructure.order.OrderMapper;
import com.academy.orders.infrastructure.product.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.academy.orders.infrastructure.ModelUtils.TEST_IMAGE_LINK;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderImageRepositoryImplTest {
	@InjectMocks
	private OrderImageRepositoryImpl orderImageRepository;
	@Mock
	private ImageRepository imageRepository;
	@Mock
	private OrderMapper orderMapper;
	@Mock
	private ProductMapper productMapper;

	@Test
	void loadImageForProductInOrderTest() {
		var order = ModelUtils.getOrder();
		var orderItem = order.orderItems().get(0);
		var product = orderItem.product();

		var productWithLink = Product.builder().image(TEST_IMAGE_LINK).build();
		var orderItemWithProduct = OrderItem.builder().product(productWithLink).build();
		var orderItemsWithProducts = singletonList(orderItemWithProduct);
		var orderWithMappedItems = Order.builder().orderItems(orderItemsWithProducts).build();

		when(imageRepository.getImageLinkByName(product.image())).thenReturn(TEST_IMAGE_LINK);
		when(productMapper.mapDomainImage(product, TEST_IMAGE_LINK)).thenReturn(productWithLink);
		when(orderMapper.mapOrderItemWithUpdatedProduct(orderItem, productWithLink)).thenReturn(orderItemWithProduct);
		when(orderMapper.mapOrderItemWithUpdatedProduct(orderItem, productWithLink)).thenReturn(orderItemWithProduct);
		when(orderMapper.toOrderWithItems(order, orderItemsWithProducts)).thenReturn(orderWithMappedItems);

		var actualOrder = orderImageRepository.loadImageForProductInOrder(order);

		assertEquals(orderWithMappedItems, actualOrder);

		verify(imageRepository).getImageLinkByName(product.image());
		verify(productMapper).mapDomainImage(product, TEST_IMAGE_LINK);
		verify(orderMapper).mapOrderItemWithUpdatedProduct(orderItem, productWithLink);
		verify(orderMapper).mapOrderItemWithUpdatedProduct(orderItem, productWithLink);
		verify(orderMapper).toOrderWithItems(order, orderItemsWithProducts);
	}
}
