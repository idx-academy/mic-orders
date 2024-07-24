package com.academy.orders.infrastructure.language.repository;

import com.academy.orders.domain.language.repository.LanguageRepository;
import com.academy.orders.domain.product.entity.Language;
import com.academy.orders.infrastructure.language.LanguageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class LanguageRepositoryImpl implements LanguageRepository {

	private final LanguageJpaAdapter languageJpaAdapter;
	private final LanguageMapper languageMapper;

	@Override
	public Language findByCode(String code) {
		return languageMapper.fromEntity(languageJpaAdapter.findByCode(code));
	}
}
