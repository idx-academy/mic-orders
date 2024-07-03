package com.academy.orders.infrastructure.account.repository;

import com.academy.orders.infrastructure.account.entity.AccountEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountJpaAdapter extends JpaRepository<AccountEntity, Long> {
	Optional<AccountEntity> findByEmail(String email);

	Boolean existsByEmail(String email);
}
