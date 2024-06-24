package com.academy.orders.boot.config.security;

import com.academy.orders.domain.account.entity.Account;
import java.util.Collection;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class SecurityUser extends User {
	private Long id;

	public SecurityUser(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.id = id;
	}

	public static UserDetails fromUser(Account user) {
		return new SecurityUser(user.id(), user.email(), user.password(),
				Set.of(new SimpleGrantedAuthority(user.role().name())));
	}
}
