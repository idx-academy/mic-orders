package com.academy.orders.apirest.auth.validator;

import com.academy.orders.apirest.auth.annotation.AllowedAccountId;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountIdValidatorTest {
	@InjectMocks
	private AccountIdValidator accountIdValidator;
	@Mock
	private Jwt jwt;
	@Mock
	private SecurityContext securityContext;
	@Mock
	private ConstraintValidatorContext constraintValidatorContext;
	@Mock
	private AllowedAccountId allowedAccountId;

	@Test
	void isValidReturnsTrueWhenAccountIdAreSameTest() {
		Long userId = 1L;
		String role = "ROLE_USER";
		Authentication authentication = new UsernamePasswordAuthenticationToken(jwt, "Passwd123!", List.of(() -> role));

		try (var mockedStatic = mockStatic(SecurityContextHolder.class)) {
			mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
			when(securityContext.getAuthentication()).thenReturn(authentication);
			when(jwt.getClaim("id")).thenReturn(userId);

			accountIdValidator.initialize(allowedAccountId);
			boolean valid = accountIdValidator.isValid(userId, constraintValidatorContext);

			assertTrue(valid);
			verify(securityContext).getAuthentication();
			verify(jwt).getClaim("id");
			mockedStatic.verify(SecurityContextHolder::getContext);
		}
	}

	@Test
	void isValidReturnsTrueWhenAccountHasRoleAdminTest() {
		Long userId = 1L;
		String role = "ROLE_ADMIN";
		Authentication authentication = new UsernamePasswordAuthenticationToken(jwt, "Passwd123!", List.of(() -> role));

		try (var mockedStatic = mockStatic(SecurityContextHolder.class)) {
			mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
			when(securityContext.getAuthentication()).thenReturn(authentication);
			when(jwt.getClaim("id")).thenReturn(userId);

			accountIdValidator.initialize(allowedAccountId);
			boolean valid = accountIdValidator.isValid(2L, constraintValidatorContext);

			assertTrue(valid);
			verify(securityContext).getAuthentication();
			verify(jwt).getClaim("id");
			mockedStatic.verify(SecurityContextHolder::getContext);
		}
	}

	@Test
	void isValidReturnsFalseTest() {
		Long userId = 1L;
		String role = "ROLE_USER";
		Authentication authentication = new UsernamePasswordAuthenticationToken(jwt, "Passwd123!", List.of(() -> role));

		try (var mockedStatic = mockStatic(SecurityContextHolder.class)) {
			mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
			when(securityContext.getAuthentication()).thenReturn(authentication);
			when(jwt.getClaim("id")).thenReturn(userId);

			accountIdValidator.initialize(allowedAccountId);
			boolean valid = accountIdValidator.isValid(2L, constraintValidatorContext);

			assertFalse(valid);
			verify(securityContext).getAuthentication();
			verify(jwt).getClaim("id");
			mockedStatic.verify(SecurityContextHolder::getContext);
		}
	}
}
