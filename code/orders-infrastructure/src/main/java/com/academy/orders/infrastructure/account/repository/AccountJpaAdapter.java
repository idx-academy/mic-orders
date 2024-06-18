package com.academy.orders.infrastructure.account.repository;

import com.academy.orders.infrastructure.account.entity.AccountEntity;
import org.springframework.data.repository.CrudRepository;

public interface AccountJpaAdapter extends CrudRepository<AccountEntity, Long> {
}
