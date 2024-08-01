package com.academy.orders.boot.infrastructure.account.repository;

import com.academy.orders.boot.infrastructure.common.repository.AbstractRepository;
import com.academy.orders.domain.account.entity.enumerated.UserStatus;
import com.academy.orders.domain.account.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountRepositoryIT extends AbstractRepository {
	@Autowired
	private AccountRepository accountRepository;

	@Test
	void updateStatusOfAccountTest() {
		final var accountId = 2L;
		final var accountEmail = "user@mail.com";
		final var status = UserStatus.DEACTIVATED;

		assertDoesNotThrow(() -> accountRepository.updateStatus(accountId, status));
		final var account = accountRepository.findAccountByEmail(accountEmail);

		assertTrue(account.isPresent());
		assertEquals(status, account.get().status());
	}
}
