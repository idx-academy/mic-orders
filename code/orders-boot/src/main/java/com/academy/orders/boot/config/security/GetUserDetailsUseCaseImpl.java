package com.academy.orders.boot.config.security;

import com.academy.orders.domain.account.entity.AccountDetails;
import com.academy.orders.domain.account.usecase.GetUserDetailsUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetUserDetailsUseCaseImpl implements GetUserDetailsUseCase {
	@Override
	public AccountDetails getUserDetailsFromUserDetails(Object principal) {
		SecurityUser securityUser = (SecurityUser) principal;
		return AccountDetails.builder().id(securityUser.getId()).firstName(securityUser.getFirstName())
				.lastName(securityUser.getLastName()).build();
	}
}
