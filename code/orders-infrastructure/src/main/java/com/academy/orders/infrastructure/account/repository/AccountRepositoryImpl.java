package com.academy.orders.infrastructure.account.repository;

import com.academy.orders.domain.entity.Account;
import com.academy.orders.domain.repository.AccountRepository;
import com.academy.orders.infrastructure.account.AccountMapper;
import jakarta.persistence.EntityNotFoundException;
import com.academy.orders.infrastructure.account.entity.AccountEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AccountRepositoryImpl implements AccountRepository {
    private final AccountJpaAdapter accountJpaAdapter;
    private final AccountMapper accountMapper;

    @Override
    public Account getAccountByEmail(String email) {
        var accountEntity = accountJpaAdapter.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("Not found account with email: " + email));

        return accountMapper.fromEntity(accountEntity);
    }

    @Override
    public Account save(Account account) {
        log.info("Saving user {}:", account);
        AccountEntity accountEntity = accountMapper.toEntity(account);
        AccountEntity savedAccount = accountJpaAdapter.save(accountEntity);
        return accountMapper.fromEntity(savedAccount);
    }
}
