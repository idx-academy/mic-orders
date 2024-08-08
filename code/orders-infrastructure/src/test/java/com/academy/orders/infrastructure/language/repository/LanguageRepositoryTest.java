package com.academy.orders.infrastructure.language.repository;

import com.academy.orders.domain.language.exception.LanguageNotFoundException;
import com.academy.orders.infrastructure.language.LanguageMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static com.academy.orders.infrastructure.ModelUtils.getLanguage;
import static com.academy.orders.infrastructure.ModelUtils.getLanguageEntity;
import static com.academy.orders.infrastructure.TestConstants.LANGUAGE_EN;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LanguageRepositoryTest {
	@InjectMocks
	private LanguageRepositoryImpl languageRepository;
	@Mock
	private LanguageJpaAdapter languageJpaAdapter;
	@Mock
	private LanguageMapper languageMapper;

	@Test
	void findByCodeTest() {
		var languageEntity = getLanguageEntity();
		var language = getLanguage();

		when(languageJpaAdapter.findByCode(LANGUAGE_EN)).thenReturn(Optional.ofNullable(languageEntity));
		when(languageMapper.fromEntity(languageEntity)).thenReturn(language);

		var result = languageRepository.findByCode(LANGUAGE_EN);

		Assertions.assertEquals(language, result.get());
		verify(languageJpaAdapter).findByCode(LANGUAGE_EN);
		verify(languageMapper).fromEntity(languageEntity);
	}

	@Test
	void findByCodeNotFoundTest() {
		String code = "fr";
		when(languageJpaAdapter.findByCode(code)).thenThrow(LanguageNotFoundException.class);
		assertThrows(LanguageNotFoundException.class, () -> languageRepository.findByCode(code));
		verify(languageJpaAdapter).findByCode(code);
	}
}
