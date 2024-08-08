package com.academy.orders.boot.infrastructure.language.repository;

import com.academy.orders.boot.infrastructure.common.repository.AbstractRepository;
import com.academy.orders.domain.language.repository.LanguageRepository;
import com.academy.orders.domain.product.entity.Language;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.academy.orders.boot.TestConstants.LANGUAGE_UK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LanguageRepositoryIT extends AbstractRepository {
	@Autowired
	private LanguageRepository languageRepository;

	@Test
	void findByCodeUkrainianTest() {
		String code = LANGUAGE_UK;
		Optional<Language> language = languageRepository.findByCode(code);

		assertTrue(language.isPresent());
		assertEquals(code, language.get().code());
	}

	@Test
	void findByCodeNotFoundTest() {
		Optional<Language> language = languageRepository.findByCode("--");

		assertTrue(language.isEmpty());
	}
}
