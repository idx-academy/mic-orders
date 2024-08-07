package com.academy.orders.application.product.usecase;

import com.academy.orders.domain.product.usecase.ExtractNameFromUrlUseCase;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

@Service
public class ExtractNameFromUrlUseCaseImpl implements ExtractNameFromUrlUseCase {
	@Override
	public String extractNameFromUrl(String url) {
		return nonNull(url) ? url.substring(url.lastIndexOf('/') + 1) : null;
	}
}
