package com.academy.orders.infrastructure.language.repository;

import com.academy.orders.domain.product.entity.Language;
import com.academy.orders.infrastructure.language.entity.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LanguageJpaAdapter extends JpaRepository<LanguageEntity, Long> {
}
