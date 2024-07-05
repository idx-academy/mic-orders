package com.academy.orders.apirest.auth.validator;

import com.academy.orders.domain.account.usecase.CheckAccountIdUseCase;
import java.util.Objects;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component("checkAccountIdUseCaseImpl")
public class CheckAccountIdUseCaseImpl implements CheckAccountIdUseCase {
	@Override
	public boolean hasSameId(Long userId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Jwt principal = (Jwt) authentication.getPrincipal();
		Long claimedId = principal.getClaim("id");
		return Objects.equals(claimedId, userId);
	}
}
