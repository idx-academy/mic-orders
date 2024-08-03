package com.academy.orders.apirest.accounts.controller;

import com.academy.orders.apirest.accounts.mapper.AccountDTOMapper;
import com.academy.orders.apirest.accounts.mapper.AccountResponseDTOMapper;
import com.academy.orders.domain.account.usecase.ChangeAccountStatusUseCase;
import com.academy.orders.domain.account.usecase.GetAllUsersUseCase;
import com.academy.orders_api_rest.generated.api.UsersManagementApi;
import com.academy.orders_api_rest.generated.model.AccountFilterDTO;
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
	private final AccountResponseDTOMapper accountResponseDTOMapper;

	@Override
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public void changeUserStatus(Long userId, AccountStatusDTO status) {
		var userStatus = accountDTOMapper.mapToUserStatus(status);
		changeAccountStatusUseCase.changeStatus(userId, userStatus);
	}

	@Override
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public PageAccountsDTO getAccounts(AccountFilterDTO filterDTO, PageableDTO pageableDTO) {
		var filter = accountDTOMapper.toDomain(filterDTO);
		var pageable = accountDTOMapper.toDomain(pageableDTO);
		var accountPage = getAllUsersUseCase.getAllUsers(filter, pageable);
		return accountResponseDTOMapper.toResponse(accountPage);
	}
}
