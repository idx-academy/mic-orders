package com.academy.orders.infrastructure.language.repository;

import com.academy.orders.domain.language.repository.LanguageRepository;
import com.academy.orders.domain.product.entity.Language;
import com.academy.orders.infrastructure.language.LanguageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class LanguageRepositoryImpl implements LanguageRepository {

	private final LanguageJpaAdapter languageJpaAdapter;
	private final LanguageMapper languageMapper;

	@Override
	public Optional<Language> findById(Long id) {
		return languageJpaAdapter.findById(id).map(languageMapper::fromEntity);
	}
}
