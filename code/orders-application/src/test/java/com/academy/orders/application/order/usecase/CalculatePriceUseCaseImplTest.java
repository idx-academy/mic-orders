package com.academy.orders.application.order.usecase;

import com.academy.orders.application.ModelUtils;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CalculatePriceUseCaseImplTest {
	@InjectMocks
	private CalculatePriceUseCaseImpl calculatePriceUseCase;

	@Test
	void testCalculatePrice() {
		var product = ModelUtils.getProduct();
		var quantity = 10;
		var expectedResult = product.price().multiply(BigDecimal.valueOf(quantity));

		var actualPrice = calculatePriceUseCase.calculatePriceForOrder(product, quantity);

		assertEquals(expectedResult, actualPrice);
	}
}
