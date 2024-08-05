package com.academy.orders.application.account.usecase;

import com.academy.orders.domain.account.dto.AccountManagementFilterDto;
import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.repository.AccountRepository;
import com.academy.orders.domain.account.usecase.GetAllUsersUseCase;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllUsersUseCaseImpl implements GetAllUsersUseCase {
	private final AccountRepository accountRepository;

	@Override
	public Page<Account> getAllUsers(AccountManagementFilterDto filter, Pageable pageable) {
		if (pageable.sort().isEmpty()) {
			pageable = new Pageable(pageable.page(), pageable.size(), List.of("createdAt,desc"));
		}
		return accountRepository.getAccounts(filter, pageable);
	}
}
