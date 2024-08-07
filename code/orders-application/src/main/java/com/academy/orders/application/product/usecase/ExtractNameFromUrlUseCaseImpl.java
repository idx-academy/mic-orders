package com.academy.orders.application.product.usecase;

import com.academy.orders.domain.product.usecase.ExtractNameFromUrlUseCase;
import org.springframework.stereotype.Service;

@Service
public class ExtractNameFromUrlUseCaseImpl implements ExtractNameFromUrlUseCase {
    @Override
    public String extractNameFromUrl(String url) {
        return url.substring(url.lastIndexOf('/') + 1);
    }
}
