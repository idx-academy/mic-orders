package com.academy.orders.domain.account.usecase;

import com.academy.orders.domain.account.dto.AccountManagementFilterDto;
import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;

public interface GetAllUsersUseCase {
	Page<Account> getAllUsers(AccountManagementFilterDto filter, Pageable pageable);
}
