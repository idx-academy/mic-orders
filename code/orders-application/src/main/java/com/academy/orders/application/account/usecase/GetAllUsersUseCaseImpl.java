package com.academy.orders.application.account.usecase;

import com.academy.orders.domain.account.dto.AccountManagementFilterDto;
import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.repository.AccountRepository;
import com.academy.orders.domain.account.usecase.GetAllUsersUseCase;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAllUsersUseCaseImpl implements GetAllUsersUseCase {
	private final AccountRepository accountRepository;

	@Override
	public Page<Account> getAllUsers(AccountManagementFilterDto filter, Pageable pageable) {
		return accountRepository.getAccounts(filter, pageable);
	}
}
