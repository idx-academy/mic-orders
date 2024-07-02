package com.academy.orders.apirest.auth.validator;

import com.academy.orders.apirest.auth.annotation.AllowedAccountId;
import com.academy.orders.domain.account.entity.enumerated.Role;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class AccountIdValidator implements ConstraintValidator<AllowedAccountId, Long> {
	private Authentication authentication;

	@Override
	public void initialize(AllowedAccountId constraintAnnotation) {
		authentication = SecurityContextHolder.getContext().getAuthentication();
	}

	@Override
	public boolean isValid(Long value, ConstraintValidatorContext context) {
		Jwt principal = (Jwt) authentication.getPrincipal();
		Long accountId = principal.getClaim("id");
		return Objects.equals(accountId, value) || authentication.getAuthorities().stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ROLE_ADMIN.toString()));
	}
}
