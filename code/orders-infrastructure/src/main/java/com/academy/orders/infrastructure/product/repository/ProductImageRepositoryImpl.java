package com.academy.orders.infrastructure.product.repository;

import com.academy.orders.domain.common.respository.ImageRepository;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.repository.ProductImageRepository;
import com.academy.orders.infrastructure.product.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductImageRepositoryImpl implements ProductImageRepository {
    private final ImageRepository imageRepository;
    private final ProductMapper productMapper;

    @Override
    public Product loadImageForProduct(Product product) {
        var imageUrl = imageRepository.getImageLinkByName(product.image());
        return productMapper.mapDomainImage(product, imageUrl);
    }
}
