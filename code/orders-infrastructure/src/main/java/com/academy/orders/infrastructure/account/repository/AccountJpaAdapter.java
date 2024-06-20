package com.academy.orders.infrastructure.account.repository;

import com.academy.orders.infrastructure.account.entity.AccountEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountJpaAdapter extends CrudRepository<AccountEntity, Long> {
	Optional<AccountEntity> findByEmail(String email);

	Boolean existsByEmail(String email);
}
