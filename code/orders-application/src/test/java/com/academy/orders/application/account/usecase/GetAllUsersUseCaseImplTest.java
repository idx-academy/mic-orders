package com.academy.orders.application.account.usecase;

import com.academy.orders.domain.account.dto.AccountManagementFilterDto;
import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.repository.AccountRepository;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllUsersUseCaseImplTest {
	@Mock
	private AccountRepository accountRepository;
	@InjectMocks
	private GetAllUsersUseCaseImpl getAllUsersUseCase;
	@InjectMocks
	private Pageable pageable;
	@InjectMocks
	private AccountManagementFilterDto filter;
	@InjectMocks
	private Page<Account> accountPage;

	@BeforeEach
	void setUp() {
		pageable = Pageable.builder().page(0).size(10).sort(Collections.emptyList()).build();
		filter = AccountManagementFilterDto.builder().build();
	}

	@Test
	void getAllUsers_withEmptySort_shouldApplyDefaultSortTest() {
		when(accountRepository.getAccounts(any(AccountManagementFilterDto.class), any(Pageable.class)))
				.thenAnswer(invocation -> {
					Pageable pageableArgument = invocation.getArgument(1);
					assertEquals(List.of("createdAt,desc"), pageableArgument.sort());
					return accountPage;
				});

		Page<Account> result = getAllUsersUseCase.getAllUsers(filter, pageable);

		assertEquals(accountPage, result);
	}

	@Test
	void getAllUsers_withNonEmptySort_shouldNotChangeSortTest() {
		Pageable pageableWithSort = Pageable.builder().page(0).size(10).sort(List.of("email,asc")).build();

		when(accountRepository.getAccounts(any(AccountManagementFilterDto.class), eq(pageableWithSort)))
				.thenReturn(accountPage);

		Page<Account> result = getAllUsersUseCase.getAllUsers(filter, pageableWithSort);

		assertEquals(accountPage, result);
	}
}
