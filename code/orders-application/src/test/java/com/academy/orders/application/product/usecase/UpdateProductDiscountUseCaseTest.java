package com.academy.orders.application.product.usecase;

import com.academy.orders.domain.discount.entity.Discount;
import com.academy.orders.domain.discount.repository.DiscountRepository;
import com.academy.orders.domain.product.exception.ProductNotFoundException;
import com.academy.orders.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.academy.orders.application.ModelUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateProductDiscountUseCaseTest {
	@InjectMocks
	private UpdateProductDiscountUseCaseImpl updateProductDiscountUseCase;
	@Mock
	private ProductRepository productRepository;
	@Mock
	private DiscountRepository discountRepository;
	@Captor
	private ArgumentCaptor<Discount> discountCaptor;

	@Test
	void updateDiscountWhenProductNotFoundTest() {
		var updateProductDiscountRequestDto = getUpdateProductDiscountRequestDto();

		when(productRepository.getById(updateProductDiscountRequestDto.productId())).thenReturn(Optional.empty());
		assertThrows(ProductNotFoundException.class,
				() -> updateProductDiscountUseCase.updateDiscount(updateProductDiscountRequestDto));
	}

	@Test
	void updateDiscountWhenDiscountAddedBeforeTest() {
		var updateProductDiscountRequestDto = getUpdateProductDiscountRequestDto();
		var productWithDiscount = getProductWithImageUrlAndDiscount();
		var discountId = productWithDiscount.getDiscount().getId();

		when(productRepository.getById(updateProductDiscountRequestDto.productId()))
				.thenReturn(Optional.of(productWithDiscount));
		when(discountRepository.deleteById(productWithDiscount.getDiscount().getId())).thenReturn(true);
		when(productRepository.updateProductDiscount(eq(updateProductDiscountRequestDto.productId()),
				any(Discount.class))).thenReturn(true);

		updateProductDiscountUseCase.updateDiscount(updateProductDiscountRequestDto);

		verify(productRepository, times(1)).getById(updateProductDiscountRequestDto.productId());
		verify(discountRepository).deleteById(discountId);
		verify(productRepository).updateProductDiscount(eq(updateProductDiscountRequestDto.productId()),
				discountCaptor.capture());

		var newDiscount = discountCaptor.getValue();
		assertNotNull(newDiscount);
		assertNull(newDiscount.getId());
		assertEquals(updateProductDiscountRequestDto.amount(), newDiscount.getAmount());
		assertEquals(updateProductDiscountRequestDto.startDate(), newDiscount.getStartDate());
		assertEquals(updateProductDiscountRequestDto.endDate(), newDiscount.getEndDate());
	}

	@Test
	void updateDiscountTest() {
		var updateProductDiscountRequestDto = getUpdateProductDiscountRequestDto();
		var product = getProductWithImageLink();

		when(productRepository.getById(updateProductDiscountRequestDto.productId())).thenReturn(Optional.of(product));
		when(productRepository.updateProductDiscount(eq(updateProductDiscountRequestDto.productId()),
				any(Discount.class))).thenReturn(true);

		updateProductDiscountUseCase.updateDiscount(updateProductDiscountRequestDto);

		verify(productRepository, times(1)).getById(updateProductDiscountRequestDto.productId());
		verify(productRepository).updateProductDiscount(eq(updateProductDiscountRequestDto.productId()),
				discountCaptor.capture());
		verifyNoInteractions(discountRepository);

		var newDiscount = discountCaptor.getValue();
		assertNotNull(newDiscount);
		assertNull(newDiscount.getId());
		assertEquals(updateProductDiscountRequestDto.amount(), newDiscount.getAmount());
		assertEquals(updateProductDiscountRequestDto.startDate(), newDiscount.getStartDate());
		assertEquals(updateProductDiscountRequestDto.endDate(), newDiscount.getEndDate());
	}
}
