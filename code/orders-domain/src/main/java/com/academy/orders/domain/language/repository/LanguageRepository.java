package com.academy.orders.domain.language.repository;

import com.academy.orders.domain.product.entity.Language;

public interface LanguageRepository {

	Language findByCode(String code);
}
