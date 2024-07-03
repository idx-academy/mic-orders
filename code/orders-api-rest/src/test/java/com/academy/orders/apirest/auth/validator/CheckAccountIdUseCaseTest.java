package com.academy.orders.apirest.auth.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
public class CheckAccountIdUseCaseTest {
	@InjectMocks
	private CheckAccountIdUseCaseImpl checkAccountIdUseCase;
	@Mock
	private Jwt jwt;
	@Mock
	private SecurityContext securityContext;
	@Mock
	private Authentication authentication;

	@Test
	void isValidReturnsTrueWhenAccountIdAreSameTest() {
		Long userId = 1L;

		try (var mockedStatic = mockStatic(SecurityContextHolder.class)) {
			mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
			when(authentication.getPrincipal()).thenReturn(jwt);
			when(securityContext.getAuthentication()).thenReturn(authentication);
			when(jwt.getClaim("id")).thenReturn(userId);

			boolean valid = checkAccountIdUseCase.hasSameId(userId);

			assertTrue(valid);
			verify(securityContext).getAuthentication();
			verify(jwt).getClaim("id");
			mockedStatic.verify(SecurityContextHolder::getContext);
		}
	}

	@Test
	void isValidReturnsTrueWhenAccountHasRoleAdminTest() {
		Long userId = 1L;

		try (var mockedStatic = mockStatic(SecurityContextHolder.class)) {
			mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
			when(authentication.getPrincipal()).thenReturn(jwt);
			when(securityContext.getAuthentication()).thenReturn(authentication);
			when(jwt.getClaim("id")).thenReturn(userId);

			boolean valid = checkAccountIdUseCase.hasSameId(userId);

			assertTrue(valid);
			verify(securityContext).getAuthentication();
			verify(jwt).getClaim("id");
			mockedStatic.verify(SecurityContextHolder::getContext);
		}
	}

	@Test
	void isValidReturnsFalseTest() {
		Long userId = 1L;
		Long enteredId = 2L;

		try (var mockedStatic = mockStatic(SecurityContextHolder.class)) {
			mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
			when(authentication.getPrincipal()).thenReturn(jwt);
			when(securityContext.getAuthentication()).thenReturn(authentication);
			when(jwt.getClaim("id")).thenReturn(userId);

			boolean valid = checkAccountIdUseCase.hasSameId(enteredId);

			assertFalse(valid);
			verify(securityContext).getAuthentication();
			verify(jwt).getClaim("id");
			mockedStatic.verify(SecurityContextHolder::getContext);
		}
	}
}
