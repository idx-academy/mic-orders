package com.academy.orders.boot.config.security;

import com.academy.orders.ModelUtils;
import com.academy.orders.domain.account.repository.AccountRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceTest {
	@InjectMocks
	private UserDetailsServiceImpl userDetailsService;
	@Mock
	private AccountRepository accountRepository;

	@Test
	void loadUserByUsernameTest() {
		var account = ModelUtils.getAccount();
		when(accountRepository.findAccountByEmail(Mockito.anyString())).thenReturn(Optional.of(account));

		var result = (SecurityUser) userDetailsService.loadUserByUsername(account.email());

		assertEquals(result.getUsername(), account.email());
		assertEquals(result.getPassword(), account.password());
		assertEquals(result.getId(), account.id());
		assertEquals(result.getFirstName(), account.firstName());
		assertEquals(result.getLastName(), account.lastName());
		assertTrue(result.getAuthorities().contains(new SimpleGrantedAuthority(account.role().name())));

		verify(accountRepository).findAccountByEmail(Mockito.anyString());
	}

	@Test
	void loadUserByUsernameThrowsUsernameNotFoundExceptionTest() {
		when(accountRepository.findAccountByEmail(Mockito.anyString())).thenReturn(Optional.empty());

		assertThrowsExactly(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(anyString()));
		verify(accountRepository).findAccountByEmail(Mockito.anyString());
	}
}