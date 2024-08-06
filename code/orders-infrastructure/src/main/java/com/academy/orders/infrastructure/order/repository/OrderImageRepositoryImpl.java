package com.academy.orders.infrastructure.order.repository;

import com.academy.orders.domain.common.respository.ImageRepository;
import com.academy.orders.domain.order.entity.Order;
import com.academy.orders.domain.order.entity.OrderItem;
import com.academy.orders.domain.order.repository.OrderImageRepository;
import com.academy.orders.infrastructure.order.OrderMapper;
import com.academy.orders.infrastructure.product.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderImageRepositoryImpl implements OrderImageRepository {
    private final ImageRepository imageRepository;
    private final OrderMapper orderMapper;
    private final ProductMapper productMapper;

    @Override
    public Order loadImageForProductInOrder(Order order) {
        var orderItems = order.orderItems().stream()
            .map(this::loadOrderItemForProductInOrderItem)
            .toList();

        return orderMapper.toOrderWithItems(order, orderItems);
    }

    private OrderItem loadOrderItemForProductInOrderItem(OrderItem orderItem) {
        var product = orderItem.product();
        var link = imageRepository.getImageLinkByName(product.image());
        var productWithLink = productMapper.mapDomainImage(product, link);
        return orderMapper.mapOrderItemWithUpdatedProduct(orderItem, productWithLink);
    }
}
