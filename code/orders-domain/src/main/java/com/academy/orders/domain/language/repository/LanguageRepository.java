package com.academy.orders.domain.language.repository;

import com.academy.orders.domain.product.entity.Language;

import java.util.Optional;

public interface LanguageRepository {
	Optional<Language> findByCode(String code);
}
