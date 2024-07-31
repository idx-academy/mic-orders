package com.academy.orders.apirest.accounts.controller;

import com.academy.orders.apirest.accounts.mapper.AccountDTOMapper;
import com.academy.orders.apirest.common.ErrorHandler;
import com.academy.orders.apirest.common.TestSecurityConfig;
import com.academy.orders.domain.account.entity.enumerated.UserStatus;
import com.academy.orders.domain.account.exception.AccountNotFoundException;
import com.academy.orders.domain.account.usecase.ChangeAccountStatusUseCase;
import com.academy.orders_api_rest.generated.model.AccountStatusDTO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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


    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    @SneakyThrows
    void changeAccountStatusNotFoundTest() {
        Long id = 1L;
        UserStatus status = UserStatus.ACTIVE;

        when(accountDTOMapper.mapToUserStatus(any(AccountStatusDTO.class))).thenReturn(status);
        doThrow(AccountNotFoundException.class).when(changeAccountStatusUseCase).changeStatus(id, status);

        mockMvc.perform(patch("/v1/users/{userId}/status", id)
            .queryParam("status", status.name()))
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

        mockMvc.perform(patch("/v1/users/{userId}/status", id)
            .queryParam("status", status.name()))
            .andExpect(status().isNoContent());

        verify(accountDTOMapper).mapToUserStatus(any(AccountStatusDTO.class));
        verify(changeAccountStatusUseCase).changeStatus(id, status);
    }

}
