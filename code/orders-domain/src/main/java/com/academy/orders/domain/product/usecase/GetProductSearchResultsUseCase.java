package com.academy.orders.domain.product.usecase;

import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.domain.product.entity.Product;

public interface GetProductSearchResultsUseCase {
    Page<Product> findProductsBySearchQuery(String searchQuery, String lang, Pageable pageable);
}
