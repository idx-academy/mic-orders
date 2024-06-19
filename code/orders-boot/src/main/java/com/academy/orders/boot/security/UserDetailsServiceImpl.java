package com.academy.orders.boot.security;

import com.academy.orders.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var account = accountRepository.getAccountByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Cannot find user by email: " + username));
		return SecurityUser.fromUser(account);
	}
}
