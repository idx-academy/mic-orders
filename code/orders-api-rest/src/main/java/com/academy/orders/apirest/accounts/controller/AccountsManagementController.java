package com.academy.orders.apirest.accounts.controller;

import com.academy.orders.apirest.accounts.mapper.AccountDTOMapper;
import com.academy.orders.apirest.accounts.mapper.AccountResponseDTOMapper;
import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.usecase.ChangeAccountStatusUseCase;
import com.academy.orders.domain.account.usecase.GetAllUsersUseCase;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders_api_rest.generated.api.UsersManagementApi;
import com.academy.orders_api_rest.generated.model.AccountStatusDTO;
import com.academy.orders_api_rest.generated.model.PageAccountsDTO;
import com.academy.orders_api_rest.generated.model.PageableDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountsManagementController implements UsersManagementApi {
	private final AccountDTOMapper accountDTOMapper;
	private final ChangeAccountStatusUseCase changeAccountStatusUseCase;
	private final GetAllUsersUseCase getAllUsersUseCase;
	private final AccountResponseDTOMapper accountMapper;

	@Override
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public void changeUserStatus(Long userId, AccountStatusDTO status) {
		var userStatus = accountDTOMapper.mapToUserStatus(status);
		changeAccountStatusUseCase.changeStatus(userId, userStatus);
	}

	@Override
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public PageAccountsDTO getAccounts(PageableDTO pageableDTO) {
		Pageable pageable = Pageable.builder().page(pageableDTO.getPage()).size(pageableDTO.getSize())
				.sort(pageableDTO.getSort()).build();

		Page<Account> accountPage = getAllUsersUseCase.getAllUsers(pageable);
		return accountMapper.toResponse(accountPage);
	}
}
