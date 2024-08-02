package com.academy.orders.application.account.usecase;

import com.academy.orders.domain.account.entity.enumerated.UserStatus;
import com.academy.orders.domain.account.exception.AccountNotFoundException;
import com.academy.orders.domain.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChangeAccountStatusUseCaseImplTest {
	@InjectMocks
	private ChangeAccountStatusUseCaseImpl changeAccountStatusUseCase;
	@Mock
	private AccountRepository accountRepository;

	private Long id;
	private UserStatus status;

	@BeforeEach
	void setUp() {
		id = 1L;
		status = UserStatus.ACTIVE;
	}

	@Test
	void changeStatusThrowsNotFoundTest() {
		var exists = false;

		when(accountRepository.existsById(id)).thenReturn(exists);
		assertThrows(AccountNotFoundException.class, () -> changeAccountStatusUseCase.changeStatus(id, status));

		verify(accountRepository).existsById(id);
	}

	@Test
	void changeStatusTest() {
		var exists = true;

		when(accountRepository.existsById(id)).thenReturn(exists);
		doNothing().when(accountRepository).updateStatus(id, status);

		assertDoesNotThrow(() -> changeAccountStatusUseCase.changeStatus(id, status));
		verify(accountRepository).existsById(id);
		verify(accountRepository).updateStatus(id, status);
	}
}
