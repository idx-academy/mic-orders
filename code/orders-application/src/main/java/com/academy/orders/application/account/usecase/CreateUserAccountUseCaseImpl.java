package com.academy.orders.application.account.usecase;

import com.academy.orders.domain.account.entity.CreateAccountDTO;
import com.academy.orders.domain.account.exception.AccountAlreadyExistsException;
import com.academy.orders.domain.account.repository.AccountRepository;
import com.academy.orders.domain.account.usecase.CreateUserAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor
public class CreateUserAccountUseCaseImpl implements CreateUserAccountUseCase {
	private final AccountRepository accountRepository;

	@Override
	public void create(CreateAccountDTO account) {
		if (TRUE.equals(accountRepository.existsByEmail(account.email()))) {
			throw new AccountAlreadyExistsException(account.email());
		}
		accountRepository.save(account);
	}
}
