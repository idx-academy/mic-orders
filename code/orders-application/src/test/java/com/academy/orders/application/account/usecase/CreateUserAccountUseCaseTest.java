package com.academy.orders.application.account.usecase;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.entity.CreateAccountDTO;
import com.academy.orders.domain.account.exception.AccountAlreadyExistsException;
import com.academy.orders.domain.account.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateUserAccountUseCaseTest {
	@Mock
	private AccountRepository accountRepository;
	@InjectMocks
	private CreateUserAccountUseCaseImpl createUserAccountUseCase;

	@Test
	void createTest() {
		CreateAccountDTO createAccountDTO = CreateAccountDTO.builder().email("newuser@mail.com").password("Admin_1234")
				.firstName("John").lastName("Doe").build();
		Account account = Account.builder().id(1L).firstName(createAccountDTO.firstName())
				.lastName(createAccountDTO.lastName()).email(createAccountDTO.email()).password("password").build();
		when(accountRepository.existsByEmail(createAccountDTO.email())).thenReturn(false);
		when(accountRepository.save(createAccountDTO)).thenReturn(account);

		createUserAccountUseCase.create(createAccountDTO);

		verify(accountRepository).existsByEmail(createAccountDTO.email());
		verify(accountRepository).save(createAccountDTO);
	}

	@Test
	void createThrowsAccountAlreadyExistsExceptionTest() {
		CreateAccountDTO createAccountDTO = CreateAccountDTO.builder().email("newuser@mail.com").password("Admin_1234")
				.firstName("John").lastName("Doe").build();

		when(accountRepository.existsByEmail(createAccountDTO.email())).thenReturn(true);

		assertThrows(AccountAlreadyExistsException.class, () -> createUserAccountUseCase.create(createAccountDTO));
	}
}
