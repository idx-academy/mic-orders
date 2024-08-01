package com.academy.orders.infrastructure.account.repository;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.entity.CreateAccountDTO;
import com.academy.orders.domain.account.entity.enumerated.Role;
import com.academy.orders.domain.account.entity.enumerated.UserStatus;
import com.academy.orders.domain.account.repository.AccountRepository;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders.infrastructure.account.AccountMapper;
import com.academy.orders.infrastructure.account.entity.AccountEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AccountRepositoryImpl implements AccountRepository {
	private final AccountJpaAdapter accountJpaAdapter;
	private final AccountMapper accountMapper;

	@Override
	public Optional<Account> getAccountByEmail(String email) {
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
	public Page<Account> getAccounts(Pageable pageable) {
		List<Order> orders = new ArrayList<>();
		List<String> sortParams = pageable.sort().stream().toList();

		for (int i = 0; i < sortParams.size(); i += 2) {
			String property = sortParams.get(i).trim();
			String directionPart = (i + 1 < sortParams.size()) ? sortParams.get(i + 1).trim() : "asc";
			Sort.Direction direction = directionPart.equalsIgnoreCase("desc")
					? Sort.Direction.DESC
					: Sort.Direction.ASC;
			orders.add(new Order(direction, property));
		}

		Sort sort = Sort.by(orders);
		var pageRequest = PageRequest.of(pageable.page(), pageable.size(), sort);
		var accountPage = accountJpaAdapter.findAll(pageRequest);

		var content = accountPage.getContent().stream().map(accountMapper::fromEntity).toList();

		return Page.<Account>builder().totalElements(accountPage.getTotalElements())
				.totalPages(accountPage.getTotalPages()).first(accountPage.isFirst()).last(accountPage.isLast())
				.number(accountPage.getNumber()).numberOfElements(accountPage.getNumberOfElements())
				.size(accountPage.getSize()).empty(accountPage.isEmpty()).content(content).build();
	}
}
