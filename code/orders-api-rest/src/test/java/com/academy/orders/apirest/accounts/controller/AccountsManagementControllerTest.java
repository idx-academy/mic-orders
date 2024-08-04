package com.academy.orders.apirest.accounts.controller;

import com.academy.orders.apirest.accounts.mapper.AccountDTOMapper;
import com.academy.orders.apirest.accounts.mapper.AccountResponseDTOMapper;
import com.academy.orders.apirest.common.ErrorHandler;
import com.academy.orders.apirest.common.TestSecurityConfig;
import com.academy.orders.domain.account.dto.AccountManagementFilterDto;
import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.entity.enumerated.Role;
import com.academy.orders.domain.account.entity.enumerated.UserStatus;
import com.academy.orders.domain.account.exception.AccountNotFoundException;
import com.academy.orders.domain.account.usecase.ChangeAccountStatusUseCase;
import com.academy.orders.domain.account.usecase.GetAllUsersUseCase;
import com.academy.orders.domain.common.Page;
import com.academy.orders.domain.common.Pageable;
import com.academy.orders_api_rest.generated.model.AccountFilterDTO;
import com.academy.orders_api_rest.generated.model.AccountStatusDTO;
import com.academy.orders_api_rest.generated.model.PageAccountsDTO;
import com.academy.orders_api_rest.generated.model.PageableDTO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountsManagementController.class)
@ContextConfiguration(classes = {AccountsManagementController.class})
@Import(value = {AopAutoConfiguration.class, TestSecurityConfig.class, ErrorHandler.class})
class AccountsManagementControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private AccountDTOMapper accountDTOMapper;
	@MockBean
	private ChangeAccountStatusUseCase changeAccountStatusUseCase;
	@MockBean
	private GetAllUsersUseCase getAllUsersUseCase;
	@MockBean
	private AccountResponseDTOMapper accountResponseDTOMapper;

	@Test
	@WithMockUser(authorities = "ROLE_ADMIN")
	@SneakyThrows
	void changeAccountStatusNotFoundTest() {
		Long id = 1L;
		UserStatus status = UserStatus.ACTIVE;

		when(accountDTOMapper.mapToUserStatus(any(AccountStatusDTO.class))).thenReturn(status);
		doThrow(AccountNotFoundException.class).when(changeAccountStatusUseCase).changeStatus(id, status);

		mockMvc.perform(patch("/v1/management/users/{userId}/status", id).queryParam("status", status.name()))
				.andExpect(status().isNotFound());

		verify(accountDTOMapper).mapToUserStatus(any(AccountStatusDTO.class));
		verify(changeAccountStatusUseCase).changeStatus(id, status);
	}

	@Test
	@WithMockUser(authorities = "ROLE_ADMIN")
	@SneakyThrows
	void changeAccountStatusTest() {
		Long id = 1L;
		UserStatus status = UserStatus.ACTIVE;

		when(accountDTOMapper.mapToUserStatus(any(AccountStatusDTO.class))).thenReturn(status);
		doNothing().when(changeAccountStatusUseCase).changeStatus(id, status);

		mockMvc.perform(patch("/v1/management/users/{userId}/status", id).queryParam("status", status.name()))
				.andExpect(status().isNoContent());

		verify(accountDTOMapper).mapToUserStatus(any(AccountStatusDTO.class));
		verify(changeAccountStatusUseCase).changeStatus(id, status);
	}

	@Test
	@WithMockUser(authorities = "ROLE_ADMIN")
	void getAccountsTest() throws Exception {
		PageableDTO pageableDTO = new PageableDTO();
		pageableDTO.setPage(0);
		pageableDTO.setSize(5);
		pageableDTO.setSort(List.of("createdAt,desc"));

		AccountManagementFilterDto filterDto = AccountManagementFilterDto.builder().status(UserStatus.ACTIVE)
				.role(Role.ROLE_USER).build();

		Pageable pageable = new Pageable(0, 5, List.of("createdAt,desc"));

		when(accountDTOMapper.toDomain(any(AccountFilterDTO.class))).thenReturn(filterDto);
		when(accountDTOMapper.toDomain(any(PageableDTO.class))).thenReturn(pageable);

		Page<Account> accountPage = Page.<Account>builder().totalElements(0L).totalPages(0).first(true).last(true)
				.number(0).numberOfElements(0).size(5).empty(true).content(Collections.emptyList()).build();

		PageAccountsDTO pageAccountsDTO = new PageAccountsDTO();

		when(getAllUsersUseCase.getAllUsers(filterDto, pageable)).thenReturn(accountPage);
		when(accountResponseDTOMapper.toResponse(accountPage)).thenReturn(pageAccountsDTO);

		var result = mockMvc.perform(get("/v1/management/users").queryParam("page", "0").queryParam("size", "5")
				.queryParam("sort", "createdAt,desc")).andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse().getContentAsString()).isNotNull();

		Mockito.verify(accountDTOMapper).toDomain(any(AccountFilterDTO.class));
		Mockito.verify(accountDTOMapper).toDomain(any(PageableDTO.class));
		Mockito.verify(getAllUsersUseCase).getAllUsers(filterDto, pageable);
		Mockito.verify(accountResponseDTOMapper).toResponse(accountPage);
	}

	@Test
	@WithMockUser(authorities = "ROLE_ADMIN")
	void getAccountsTest1() throws Exception {
		PageableDTO pageableDTO = new PageableDTO();
		pageableDTO.setPage(0);
		pageableDTO.setSize(5);
		pageableDTO.setSort(List.of("createdAt,desc"));

		AccountManagementFilterDto filterDto = AccountManagementFilterDto.builder().status(UserStatus.ACTIVE)
				.role(Role.ROLE_USER).build();

		Pageable pageable = new Pageable(0, 5, List.of("createdAt,desc"));

		when(accountDTOMapper.toDomain(any(AccountFilterDTO.class))).thenReturn(filterDto);
		when(accountDTOMapper.toDomain(any(PageableDTO.class))).thenReturn(pageable);

		Account account = new Account(1L, "username", "firstName", "lastName", "email@example.com", Role.ROLE_USER,
				UserStatus.ACTIVE, LocalDateTime.now());

		Page<Account> accountPage = Page.<Account>builder().totalElements(1L).totalPages(1).first(true).last(true)
				.number(0).numberOfElements(1).size(5).empty(false).content(Collections.singletonList(account)).build();

		PageAccountsDTO pageAccountsDTO = new PageAccountsDTO();
		pageAccountsDTO.setTotalElements(1L);
		pageAccountsDTO.setTotalPages(1);
		pageAccountsDTO.setFirst(true);
		pageAccountsDTO.setLast(true);
		pageAccountsDTO.setNumber(0);
		pageAccountsDTO.setNumberOfElements(1);
		pageAccountsDTO.setSize(5);
		pageAccountsDTO.setEmpty(false);
		pageAccountsDTO.setContent(Collections.emptyList());

		when(getAllUsersUseCase.getAllUsers(filterDto, pageable)).thenReturn(accountPage);
		when(accountResponseDTOMapper.toResponse(accountPage)).thenReturn(pageAccountsDTO);

		var result = mockMvc.perform(get("/v1/management/users").queryParam("page", "0").queryParam("size", "5")
				.queryParam("sort", "createdAt,desc")).andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse().getContentAsString()).isNotNull();

		var captor = ArgumentCaptor.forClass(Page.class);
		verify(accountDTOMapper).toDomain(any(AccountFilterDTO.class));
		verify(accountDTOMapper).toDomain(any(PageableDTO.class));
		verify(getAllUsersUseCase).getAllUsers(filterDto, pageable);
		verify(accountResponseDTOMapper).toResponse(captor.capture());

		var capturedPage = captor.getValue();
		assertThat(capturedPage).isEqualTo(accountPage);

		assertThat(pageAccountsDTO.getTotalElements()).isEqualTo(1L);
		assertThat(pageAccountsDTO.getTotalPages()).isEqualTo(1);
		assertThat(pageAccountsDTO.getFirst()).isTrue();
		assertThat(pageAccountsDTO.getLast()).isTrue();
		assertThat(pageAccountsDTO.getNumber()).isZero();
		assertThat(pageAccountsDTO.getNumberOfElements()).isEqualTo(1);
		assertThat(pageAccountsDTO.getSize()).isEqualTo(5);
		assertThat(pageAccountsDTO.getEmpty()).isFalse();
	}
}
