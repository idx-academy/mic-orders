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
		return SecurityUser.fromUser(account);
	}

	private Account getAccount(String username) {
		var account = accountRepository.getAccountByEmail(username);
		if (account.isEmpty() || account.get().status().equals(DEACTIVATED)) {
			throw new UsernameNotFoundException(
                format("Account does not exist with email %s or account is disabled",username));
		}
		return account.get();
	}
}
