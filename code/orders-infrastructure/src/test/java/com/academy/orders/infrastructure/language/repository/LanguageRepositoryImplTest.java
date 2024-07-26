package com.academy.orders.infrastructure.language.repository;

import com.academy.orders.domain.product.entity.Language;
import com.academy.orders.infrastructure.language.LanguageMapper;
import com.academy.orders.infrastructure.language.entity.LanguageEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LanguageRepositoryImplTest {
	@InjectMocks
	private LanguageRepositoryImpl languageRepository;
	@Mock
	private LanguageJpaAdapter languageJpaAdapter;
	@Mock
	private LanguageMapper languageMapper;

	private LanguageEntity languageEntity;
	private Language language;

	@BeforeEach
	void setUp() {
		languageEntity = new LanguageEntity();
		languageEntity.setId(1L);
		languageEntity.setCode("en");

		language = new Language(1L, "en");
	}

	@Test
	void findByCodeTest() {
		String code = "en";
		when(languageJpaAdapter.findByCode(code)).thenReturn(languageEntity);
		when(languageMapper.fromEntity(languageEntity)).thenReturn(language);

		Language result = languageRepository.findByCode(code);

		Assertions.assertEquals(language, result);
		verify(languageJpaAdapter, times(1)).findByCode(code);
		verify(languageMapper, times(1)).fromEntity(languageEntity);
	}

	@Test
	void findByCode_NotFoundTest() {
		String code = "fr";
		when(languageJpaAdapter.findByCode(code)).thenReturn(null);
		when(languageMapper.fromEntity(null)).thenReturn(null);

		Language result = languageRepository.findByCode(code);

		Assertions.assertNull(result);
		verify(languageJpaAdapter, times(1)).findByCode(code);
		verify(languageMapper, times(1)).fromEntity(null);
	}
}
