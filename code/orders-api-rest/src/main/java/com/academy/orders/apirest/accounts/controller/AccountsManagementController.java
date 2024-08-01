package com.academy.orders.apirest.accounts.controller;

import com.academy.orders.apirest.accounts.mapper.AccountDTOMapper;
import com.academy.orders.domain.account.usecase.ChangeAccountStatusUseCase;
import com.academy.orders_api_rest.generated.api.UsersManagementApi;
import com.academy.orders_api_rest.generated.model.AccountStatusDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountsManagementController implements UsersManagementApi {
	private final AccountDTOMapper accountDTOMapper;
	private final ChangeAccountStatusUseCase changeAccountStatusUseCase;

	@Override
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public void changeUserStatus(Long userId, AccountStatusDTO status) {
		var userStatus = accountDTOMapper.mapToUserStatus(status);
		changeAccountStatusUseCase.changeStatus(userId, userStatus);
	}
}
