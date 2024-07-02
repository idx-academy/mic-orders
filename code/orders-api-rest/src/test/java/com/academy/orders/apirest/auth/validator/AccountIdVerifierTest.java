package com.academy.orders.apirest.auth.validator;

import com.academy.orders.apirest.auth.annotation.AllowedAccountId;
import com.academy.orders.domain.account.usecase.GetAccountDetailsUseCase;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountIdVerifierTest {
	@InjectMocks
	private AccountIdVerifier accountIdVerifier;
	@Mock
	private GetAccountDetailsUseCase detailsUseCase;
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
		Authentication authentication = new UsernamePasswordAuthenticationToken(detailsUseCase, "Passwd123!",
				List.of(() -> role));

		try (var mockedStatic = mockStatic(SecurityContextHolder.class)) {
			mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
			when(securityContext.getAuthentication()).thenReturn(authentication);
			when(detailsUseCase.getId()).thenReturn(userId);

			accountIdVerifier.initialize(allowedAccountId);
			boolean valid = accountIdVerifier.isValid(userId, constraintValidatorContext);

			assertTrue(valid);
			verify(securityContext).getAuthentication();
			verify(detailsUseCase).getId();
			mockedStatic.verify(SecurityContextHolder::getContext);
		}
	}

	@Test
	void isValidReturnsTrueWhenAccountHasRoleAdminTest() {
		Long userId = 1L;
		String role = "ROLE_ADMIN";
		Authentication authentication = new UsernamePasswordAuthenticationToken(detailsUseCase, "Passwd123!",
				List.of(() -> role));

		try (var mockedStatic = mockStatic(SecurityContextHolder.class)) {
			mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
			when(securityContext.getAuthentication()).thenReturn(authentication);
			when(detailsUseCase.getId()).thenReturn(userId);

			accountIdVerifier.initialize(allowedAccountId);
			boolean valid = accountIdVerifier.isValid(2L, constraintValidatorContext);

			assertTrue(valid);
			verify(securityContext).getAuthentication();
			verify(detailsUseCase).getId();
			mockedStatic.verify(SecurityContextHolder::getContext);
		}
	}

	@Test
	void isValidReturnsFalseTest() {
		Long userId = 1L;
		String role = "ROLE_USER";
		Authentication authentication = new UsernamePasswordAuthenticationToken(detailsUseCase, "Passwd123!",
				List.of(() -> role));

		try (var mockedStatic = mockStatic(SecurityContextHolder.class)) {
			mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
			when(securityContext.getAuthentication()).thenReturn(authentication);
			when(detailsUseCase.getId()).thenReturn(userId);

			accountIdVerifier.initialize(allowedAccountId);
			boolean valid = accountIdVerifier.isValid(2L, constraintValidatorContext);

			assertFalse(valid);
			verify(securityContext).getAuthentication();
			verify(detailsUseCase).getId();
			mockedStatic.verify(SecurityContextHolder::getContext);
		}
	}
}
