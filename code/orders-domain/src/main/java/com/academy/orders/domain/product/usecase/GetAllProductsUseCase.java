//package com.academy.orders.domain.product.usecase;
//
//import com.academy.orders.domain.product.entity.Product;
//import java.util.List;
//
//public interface GetAllProductsUseCase {
//	List<Product> getAllProducts(String language);
//}
package com.academy.orders.domain.product.usecase;

import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.valueobject.PageRequest;
import com.academy.orders.domain.product.valueobject.PageResponse;

public interface GetAllProductsUseCase {
	PageResponse<Product> getAllProducts(String language, PageRequest pageRequest);
}
