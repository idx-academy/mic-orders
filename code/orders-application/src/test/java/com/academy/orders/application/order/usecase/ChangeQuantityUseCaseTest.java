package com.academy.orders.application.order.usecase;

import com.academy.orders.application.product.usecase.ChangeQuantityUseCaseImpl;
import com.academy.orders.domain.order.exception.InsufficientProductQuantityException;
import com.academy.orders.domain.product.entity.Product;
import com.academy.orders.domain.product.repository.ProductRepository;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.academy.orders.application.ModelUtils.getProductWithImageLink;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChangeQuantityUseCaseTest {
	@InjectMocks
	private ChangeQuantityUseCaseImpl changeQuantityUseCase;
	@Mock
	private ProductRepository productRepository;

	@ParameterizedTest
	@MethodSource("changeQuantityDataProviderTest")
	void changeQuantityTest(Product product, int quantity) {
		var quantityDifference = product.getQuantity() - quantity;
		doNothing().when(productRepository).setNewProductQuantity(product.getId(), quantityDifference);

		assertDoesNotThrow(() -> changeQuantityUseCase.changeQuantityOfProduct(product, quantity));
		verify(productRepository).setNewProductQuantity(product.getId(), quantityDifference);
	}

	private static Stream<Arguments> changeQuantityDataProviderTest() {
		var product = getProductWithImageLink();
		return Stream.of(Arguments.of(product, product.getQuantity() - 1),
				Arguments.of(product, product.getQuantity()));
	}

	@ParameterizedTest
	@MethodSource("changeQuantityThrowsExceptionProviderTest")
	void changeQuantityThrowsInsufficientProductQuantityExceptionTest(Product product, int quantity) {
		assertThrows(InsufficientProductQuantityException.class,
				() -> changeQuantityUseCase.changeQuantityOfProduct(product, quantity));

		verify(productRepository, never()).setNewProductQuantity(any(UUID.class), anyInt());
	}

	private static Stream<Arguments> changeQuantityThrowsExceptionProviderTest() {
		var product = getProductWithImageLink();
		return Stream.of(Arguments.of(product, product.getQuantity() + 10), Arguments.of(product, -10),
				Arguments.of(product, 0));
	}
}