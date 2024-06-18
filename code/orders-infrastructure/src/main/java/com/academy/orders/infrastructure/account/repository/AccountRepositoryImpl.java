package com.academy.orders.infrastructure.account.repository;

import com.academy.orders.domain.entity.Account;
import com.academy.orders.domain.repository.AccountRepository;
import com.academy.orders.infrastructure.account.AccountMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {
    private final AccountJpaAdapter accountJpaAdapter;
    private final AccountMapper accountMapper;

    @Override
    public Account getAccountByEmail(String email) {
        var accountEntity = accountJpaAdapter.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("Not found account with email: " + email));

        return accountMapper.fromEntity(accountEntity);
    }
}
