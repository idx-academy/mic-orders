package com.academy.orders.application.product.usecase;

import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.*;

@ExtendWith(MockitoExtension.class)
class ExtractNameFromUrlUseCaseTest {
	@InjectMocks
	private ExtractNameFromUrlUseCaseImpl extractNameFromUrlUseCase;

	@ParameterizedTest
	@MethodSource("extractNameFromUrlProvider")
	void extractNameFromUrlTest(String url, String expectedName) {
		var actual = extractNameFromUrlUseCase.extractNameFromUrl(url);

		assertEquals(expectedName, actual);
	}

	static Stream<Arguments> extractNameFromUrlProvider() {
		return Stream.of(of("http://localhost/name.jpg", "name.jpg"), of("just-name.jpg", "just-name.jpg"), of("", ""),
				of(null, null));
	}
}
