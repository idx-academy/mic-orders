package com.academy.orders.boot.infrastructure.account.repository;

import com.academy.orders.boot.infrastructure.common.repository.AbstractRepository;
import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.entity.CreateAccountDTO;
import com.academy.orders.domain.account.entity.enumerated.Role;
import com.academy.orders.domain.account.entity.enumerated.UserStatus;
import com.academy.orders.domain.account.repository.AccountRepository;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.*;

class AccountRepositoryIT extends AbstractRepository {
	private final Long accountId = 2L;
	private final String accountEmail = "user@mail.com";

	@Autowired
	private AccountRepository accountRepository;

	@Test
	void findAccountByEmailTest() {
		final var account = accountRepository.findAccountByEmail(accountEmail);

		assertTrue(account.isPresent());
		assertSchema(account.get(), accountEmail);
	}

	@ParameterizedTest
	@CsvSource({"user@mail.com, true", "unknown@mail.com, false"})
	void existsByEmailTest(String email, boolean expected) {
		final var actual = accountRepository.existsByEmail(email);
		assertEquals(expected, actual);
	}

	@Test
	void saveTest() {
		var createAccountDto = CreateAccountDTO.builder().email("new@mail.com").password("password")
				.firstName("firstName").lastName("lastName").build();

		final var account = accountRepository.save(createAccountDto);
		assertSchema(account, createAccountDto.email());
	}

	@Test
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	void saveWithExistentEmailTest() {
		var createAccountDto = CreateAccountDTO.builder().email(accountEmail).password("password")
				.firstName("firstName").lastName("lastName").build();
		assertThrows(DataIntegrityViolationException.class, () -> accountRepository.save(createAccountDto));
	}

	@ParameterizedTest
	@MethodSource("findRoleByEmailMethodSourceProvider")
	void findRoleByEmailTest(String email, Role expectedRole) {
		final var actualRole = accountRepository.findRoleByEmail(email);
		assertEquals(Optional.ofNullable(expectedRole), actualRole);
	}

	@ParameterizedTest
	@CsvSource({"1, true", "100, false"})
	void existsByIdTest(Long id, boolean expected) {
		final var actual = accountRepository.existsById(id);
		assertEquals(expected, actual);
	}

	@Test
	void updateStatusOfAccountTest() {
		final var status = UserStatus.DEACTIVATED;

		assertDoesNotThrow(() -> accountRepository.updateStatus(accountId, status));
		final var account = accountRepository.findAccountByEmail(accountEmail);

		assertTrue(account.isPresent());
		assertEquals(status, account.get().status());
	}

	private static void assertSchema(Account account, String email) {
		assertNotNull(account);
		assertNotNull(account.id());
		assertNotNull(account.password());
		assertNotNull(account.firstName());
		assertNotNull(account.lastName());
		assertEquals(Role.ROLE_USER, account.role());
		assertEquals(UserStatus.ACTIVE, account.status());
		assertEquals(email, account.email());
	}

	static Stream<Arguments> findRoleByEmailMethodSourceProvider() {
		return Stream.of(of("user@mail.com", Role.ROLE_USER), of("wrong@mail.com", null));
	}
}
