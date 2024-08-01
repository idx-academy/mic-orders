package com.academy.orders.boot.config.security;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.academy.orders.domain.account.entity.enumerated.UserStatus.DEACTIVATED;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var account = getAccount(username);
		checkAccountIsActive(account, username);
		return SecurityUser.fromUser(account);
	}

	private Account getAccount(String username) {
		return accountRepository.findAccountByEmail(username).orElseThrow(
				() -> new UsernameNotFoundException(format("Account does not exist with email: %s", username)));
	}

	private void checkAccountIsActive(Account account, String username) {
		if (account.status().equals(DEACTIVATED)) {
			throw new UsernameNotFoundException(format("Account with email: %s is deactivated", username));
		}
	}
}
