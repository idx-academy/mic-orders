package com.academy.orders.boot.config.security;

import com.academy.orders.domain.account.entity.Account;
import com.academy.orders.domain.account.usecase.GetAccountDetailsUseCase;
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
public class SecurityUser extends User implements GetAccountDetailsUseCase {
	private Long id;
	private String firstName;
	private String lastName;

	public SecurityUser(Long id, String firstName, String lastName, String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public static UserDetails fromUser(Account user) {
		return new SecurityUser(user.id(), user.firstName(), user.lastName(), user.email(), user.password(),
				Set.of(new SimpleGrantedAuthority(user.role().name())));
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}
}
