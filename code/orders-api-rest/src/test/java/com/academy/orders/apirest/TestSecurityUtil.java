package com.academy.orders.apirest;

import java.util.Arrays;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

public class TestSecurityUtil {
	public static RequestPostProcessor jwtAuth(long userId, String... roles) {
		var grantedRoles = Arrays.stream(roles).map(SimpleGrantedAuthority::new).map(GrantedAuthority.class::cast)
				.toList();

		return SecurityMockMvcRequestPostProcessors.jwt().jwt(builder -> builder.claim("id", userId))
				.authorities(grantedRoles);
	}
}
