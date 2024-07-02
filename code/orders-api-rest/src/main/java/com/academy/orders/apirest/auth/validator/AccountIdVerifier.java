package com.academy.orders.apirest.auth.validator;

import com.academy.orders.apirest.auth.annotation.AllowedAccountId;
import com.academy.orders.domain.account.entity.enumerated.Role;
import com.academy.orders.domain.account.usecase.GetAccountDetailsUseCase;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AccountIdVerifier implements ConstraintValidator<AllowedAccountId, Long> {
    private Authentication authentication;
    private GetAccountDetailsUseCase detailsUseCase;

    @Override
    public void initialize(AllowedAccountId constraintAnnotation) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        detailsUseCase = (GetAccountDetailsUseCase) authentication.getPrincipal();
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return Objects.equals(detailsUseCase.getId(), value) ||
               authentication.getAuthorities().stream()
                   .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ROLE_ADMIN.toString()));
    }
}
