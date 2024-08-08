package com.academy.orders.domain.language.repository;

import com.academy.orders.domain.product.entity.Language;

import java.util.Optional;

public interface LanguageRepository {
	/**
	 * Finds a language entity by its code.
	 *
	 * @param code
	 *            the code of the language to be retrieved.
	 * @return an {@link Optional} containing the {@link Language} if found, or
	 *         empty if not found.
	 */
	Optional<Language> findByCode(String code);
}
