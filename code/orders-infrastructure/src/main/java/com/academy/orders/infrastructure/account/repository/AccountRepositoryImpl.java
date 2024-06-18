package com.academy.orders.infrastructure.account.repository;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.entity.CreateAccountDTO;
import com.academy.orders.domain.account.entity.enumerated.Role;
import com.academy.orders.domain.account.entity.enumerated.UserStatus;
import com.academy.orders.domain.account.repository.AccountRepository;
import com.academy.orders.infrastructure.account.AccountMapper;
import jakarta.persistence.EntityNotFoundException;
import com.academy.orders.infrastructure.account.entity.AccountEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
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
    @Transactional
    public Account save(CreateAccountDTO account) {
        log.info("Saving user {}:", account);
        AccountEntity accountEntity = accountMapper.toEntity(account);
        accountEntity.setRole(Role.ROLE_USER);
        accountEntity.setStatus(UserStatus.ACTIVE);
        AccountEntity savedAccount = accountJpaAdapter.save(accountEntity);
        return accountMapper.fromEntity(savedAccount);
    }
}
