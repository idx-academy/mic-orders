package com.academy.orders.infrastructure.account.repository;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.entity.CreateAccountDTO;
import com.academy.orders.domain.account.entity.enumerated.Role;
import com.academy.orders.domain.account.entity.enumerated.UserStatus;
import com.academy.orders.domain.account.repository.AccountRepository;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.infrastructure.account.AccountMapper;
import com.academy.orders.infrastructure.account.AccountPageMapper;
import com.academy.orders.infrastructure.account.entity.AccountEntity;
import com.academy.orders.infrastructure.common.PageableMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AccountRepositoryImpl implements AccountRepository {
	private final AccountJpaAdapter accountJpaAdapter;
	private final AccountMapper accountMapper;
	private final AccountPageMapper accountPageMapper;
	private final PageableMapper pageableMapper;

	@Override
	public Optional<Account> findAccountByEmail(String email) {
		var accountEntity = accountJpaAdapter.findByEmail(email);
		return accountEntity.map(accountMapper::fromEntity);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return accountJpaAdapter.existsByEmail(email);
	}

	@Override
	@Transactional
	public Boolean existsById(Long id) {
		return accountJpaAdapter.existsById(id);
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

	@Override
	public Optional<Role> findRoleByEmail(String email) {
		return accountJpaAdapter.findRoleByEmail(email);
	}

	@Override
	public void updateStatus(Long id, UserStatus status) {
		accountJpaAdapter.updateStatus(id, status);
	}

	@Override
	public Page<Account> getAccounts(Pageable pageableDomain) {
		var pageable = pageableMapper.fromDomain(pageableDomain);
		var pageRequest = PageRequest.of(pageableDomain.page(), pageableDomain.size(), pageable.getSort());
		var accountPage = accountJpaAdapter.findAll(pageRequest);
		return accountPageMapper.toDomain(accountPage);
	}
}
