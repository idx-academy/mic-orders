package com.academy.orders.apirest.accounts;

import com.academy.orders.apirest.accounts.mapper.AccountResponseDTOMapper;
import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.usecase.GetAllUsersUseCase;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders_api_rest.generated.api.AccountsManagementApi;
import com.academy.orders_api_rest.generated.model.PageAccountsDTO;
import com.academy.orders_api_rest.generated.model.PageableDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class AccountsController implements AccountsManagementApi {
	private final GetAllUsersUseCase getAllUsersUseCase;
	private final AccountResponseDTOMapper accountMapper;

	@Override
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public PageAccountsDTO getAccounts(PageableDTO pageableDTO) {
		Pageable pageable = Pageable.builder().page(pageableDTO.getPage()).size(pageableDTO.getSize())
				.sort(pageableDTO.getSort()).build();

		Page<Account> accountPage = getAllUsersUseCase.getAllUsers(pageable);
		return accountMapper.toResponse(accountPage);
	}
}
